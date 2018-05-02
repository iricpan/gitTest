package cn.bd.action;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import cn.bd.action.base.BaseAction;
import cn.bd.entity.Area;
import cn.bd.service.AreaService;
import cn.bd.utils.PinYin4jUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Controller;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class AreaAction extends BaseAction<Area> {

    @Autowired
    private AreaService areaService;

    private File file;
	
	public void setFile(File file) {
		this.file = file;
	}

	@Action(value="areaAction_importXls",results={@Result(type="redirect",location="./pages/base/area.html")})
	public String importXls() throws Exception {
		//创建一个集合
		List<Area> list = new ArrayList<Area> ();
		HSSFWorkbook workbook = new HSSFWorkbook (new FileInputStream (file));
		//获取工作部的表
		HSSFSheet sheet = workbook.getSheetAt (0);
		//遍历每一行
		for (Row row : sheet) {
			if(row.getRowNum ()==0){
				continue;
			}else if(row.getCell (0)==null|| StringUtils.isBlank (row.getCell (0).getStringCellValue ())){
				continue;
			}
            Area area = new Area ( );
            String id = row.getCell (0).getStringCellValue ();
            String province = row.getCell (1).getStringCellValue ( );
            String city = row.getCell (2).getStringCellValue ( );
            String district = row.getCell (3).getStringCellValue ( );
            String postcode = row.getCell (4).getStringCellValue ( );
            area.setId (id);
            area.setProvince (province);
            area.setCity (city);
            area.setDistrict (district);
            area.setPostcode (postcode);
            //通过pinyin4j创建城市编码和简码
            province = province.substring (0,province.length ()-1);
            city=city.substring (0,city.length ()-1);
            district = district.substring (0,district.length ()-1);
            String shortcodes = province+city+district;
            String[] headByString = PinYin4jUtils.getHeadByString (shortcodes);
            StringBuilder sb = new StringBuilder ( );
            for (String s : headByString) {
                sb.append (s);
            }
            String shortcode = sb.toString ();
            String citycode = PinYin4jUtils.hanziToPinyin (city,"");
          /*  System.out.println("shortcode:"+shortcode);
            System.out.println("citycode:"+citycode);*/
            area.setCitycode (citycode);
            area.setShortcode (shortcode);
            list.add (area);

        }
        areaService.save(list);
		return SUCCESS;
	}
    //分页查询
    //含条件
    @Action(value ="areaAction_pageQuery",results = {@Result(type = "json")})
    public String pageQuery(){
        Pageable pageable = new PageRequest (page-1,rows);
        Specification<Area> specifications = new Specification<Area> ( ) {
            @Override
            public Predicate toPredicate(Root<Area> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = null;
               List<Predicate> predicates = new ArrayList<Predicate> ();
               if (StringUtils.isNotBlank (model.getProvince ())){
                   predicate = cb.like (root.get ("province").as (String.class), "%" + model.getProvince ( ) + "%");
                   predicates.add (predicate);
               }
               if(StringUtils.isNotBlank (model.getCity ())){
                   predicate = cb.like (root.get ("city").as (String.class), "%" + model.getCity () + "%");
                   predicates.add (predicate);
               }
               if(StringUtils.isNotBlank (model.getDistrict ())){
                   predicate = cb.like (root.get ("district").as (String.class), "%" + model.getDistrict () + "%");
                   predicates.add (predicate);
               }
               if (predicates!=null&&predicates.size ()!=0){
                   return cb.and (predicates.toArray (new Predicate[0]));
               }
                return null;
            }
        };
        Page<Area> pages = areaService.findPageDAtaCondition(specifications,pageable);
        System.out.println(pages);
        this.pushPageDataToValueStack (pages);
        return SUCCESS;
    }
	
}

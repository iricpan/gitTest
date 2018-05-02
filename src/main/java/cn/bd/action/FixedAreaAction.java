package cn.bd.action;

import cn.bd.action.base.BaseAction;
import cn.bd.entity.FixedArea;
import cn.bd.service.FixedAreaService;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.stereotype.Controller;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@ParentPackage(value="json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class FixedAreaAction extends BaseAction<FixedArea> {

	@Autowired
	private FixedAreaService fixedAreaService;

	//添加定区

    @Action(value = "fixedArea_save",results = {@Result(type = "redirect",location = "./pages/base/fixed_area.html")})
    public String save() {
        fixedAreaService.save(model);
        return SUCCESS;
}

    @Action(value = "fixedAreaAction_pageQuery",results = {@Result(name = "success", type = "json")})
	public String pageQuery(){
//	    System.out.println("5555555");
		Pageable pageable = new PageRequest (page - 1, rows);
		Specification<FixedArea> specification = new Specification<FixedArea> ( ) {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
				Predicate predicate=null;
				List<Predicate> predicates = new ArrayList<> ( );
				if(StringUtils.isNotBlank (model.getId ())){
					predicate = cb.equal (root.get ("id").as (String.class), model.getId ( ));
					predicates.add (predicate);
				}
				if(StringUtils.isNotBlank (model.getCompany ())){
					predicate = cb.like (root.get ("company").as (String.class), "%"+model.getCompany ()+"%");
					predicates.add (predicate);
				}
				if(predicates!=null&&predicates.size ()!=0){
					return cb.and (predicates.toArray (new Predicate[0]));
				}
				return null;
			}
		};
		Page<FixedArea> pages = fixedAreaService.findPageDataCondition(specification,pageable);
		this.pushPageDataToValueStack (pages);
		return SUCCESS;
	}
}

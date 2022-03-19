package person.zxccong.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import person.zxccong.springboot.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zxccong
 * @date 2022/3/18
 */
@RestController
@RequestMapping(value = "v1/products")
public class ProductController {

    @Autowired
    WebApplicationContext applicationContext;

    @RequestMapping(value = "v1/getAllUrl", method = RequestMethod.POST)
    public Object getAllUrl() {
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        // 获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();

//      List<String> urlList = new ArrayList<>();
//      for (RequestMappingInfo info : map.keySet()) {
//          // 获取url的Set集合，一个方法可能对应多个url
//          Set<String> patterns = info.getPatternsCondition().getPatterns();
//
//          for (String url : patterns) {
//              urlList.add(url);
//          }
//      }

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
            Map<String, String> map1 = new HashMap<String, String>();
            RequestMappingInfo info = m.getKey();
            HandlerMethod method = m.getValue();
            PatternsRequestCondition p = info.getPatternsCondition();
            for (String url : p.getPatterns()) {
                map1.put("url", url);
            }
            map1.put("className", method.getMethod().getDeclaringClass().getName()); // 类名
            map1.put("method", method.getMethod().getName()); // 方法名
            RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
            for (RequestMethod requestMethod : methodsCondition.getMethods()) {
                map1.put("type", requestMethod.toString());
            }

            list.add(map1);
        }
        return list;
    }
    @GetMapping("/{productCode}")
    public Product getProduct(@PathVariable String productCode) {
         Product product = new Product();
        product.setId(1L);
        product.setPrice(100F);
        product.setProductCode("product001");
        product.setProductName("New Product");
        product.setDescription("Description of The Product");
        return product;
    }
}

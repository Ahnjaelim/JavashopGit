package kr.co.javashop.controller;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j2;


@Controller
@Log4j2
public class SampleController {

	@GetMapping("/sample")
	public void hello(Model model) {
		log.info("Sample Controller Test");
		model.addAttribute("msg", "HELLO WORLD");
	}
	
	@GetMapping("/sampleapi")
	public void sampleapi(Model model) throws Exception {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/6410000/busrouteservice/getBusRouteInfoItem"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=bPMSR8AdZhjo3e3j6V%2Fjn2BTTDfiUFn3FbCDEb%2Buy0ssYukFh8erRdVm2vbw7bJLHgyIJ55yvzzj5Bd4IJUFsg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder	.encode("routeId","UTF-8") + "=" + URLEncoder.encode("200000085", "UTF-8")); /*노선ID*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/xml");
        System.out.println("Response code: " + conn.getResponseCode());
        
        SAXBuilder builder = new SAXBuilder();
        String queryTime = "";
        Document document = builder.build(conn.getInputStream());
        Element root = document.getRootElement();
        List xmlelement = root.getChildren();
        Iterator it = xmlelement.iterator();
        
        List msgHeaderList = null;
        List msgBodyList = null;
        List busRouteInfoItemList = null;
        
        while(it.hasNext()) {
        	Element e = (Element)it.next();
        	System.out.println(e.getName());
        	if(e.getName()=="msgHeader") {
        		msgHeaderList = e.getChildren();
        	}
        	if(e.getName()=="msgBody") {
        		msgBodyList = e.getChildren();
        	}
        	
        }
        
        System.out.println("<msgHeader>");
        Iterator msgHeaderIt = msgHeaderList.iterator();
        while(msgHeaderIt.hasNext()) {
        	Element e = (Element)msgHeaderIt.next();
        	System.out.println(e.getName()+" : "+e.getValue());
        }
        
        System.out.println("<msgBody>");
        Iterator msgBodyIt = msgBodyList.iterator();
        while(msgBodyIt.hasNext()) {
        	Element e = (Element)msgBodyIt.next();
        	System.out.println(e.getName()+" : "+e.getValue());
        	if(e.getName()=="busRouteInfoItem") {
        		busRouteInfoItemList = e.getChildren();
        	}
        }
        
        System.out.println("<busRouteInfoItem>");
        HashMap<String, String> resultMap = new HashMap<>();
        Iterator busRouteInfoItemIt = busRouteInfoItemList.iterator();
        while(busRouteInfoItemIt.hasNext()) {
        	Element e = (Element)busRouteInfoItemIt.next();
        	System.out.println(e.getName()+" : "+e.getValue());
        	resultMap.put(e.getName(), e.getValue());
        }
        
        model.addAttribute("response", resultMap);
	}
}

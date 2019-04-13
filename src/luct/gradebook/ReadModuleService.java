package luct.gradebook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javafx.concurrent.Task;

public class ReadModuleService extends Task<List<Module>>{

	@Override
	protected List<Module> call() throws Exception {
		String url = "https://cmslesotho.limkokwing.net/campus/lecturer/f_modulelecturerviewlist.php?cmd=resetall";
		Response page = Session.get(url);
		List<Module> list = new ArrayList<>();
		try {
			Document doc = page.parse();
			Element table = doc.getElementById("ewlistmain");
			for(Element tr: table.getElementsByTag("tr")) {
				if(tr.attr("class").equals("ewTableHeader")) {
					continue;
				}
				Elements data = tr.getElementsByTag("td");
				Module module = new Module();
				module.setCode(data.get(0).text());
				module.setName(data.get(1).text());
				Element a = data.get(3).getElementsByTag("a").first();
				String id = a.attr("href");
				id = id.substring(id.lastIndexOf('=')+1, id.length());
				module.setId(id);
				list.add(module);
				System.out.println(module);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
}

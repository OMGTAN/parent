package com.tan.test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.Includes;
import com.tan.test.entity.AddrModel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Poitl {

	public static void main(String[] args) {

		Map<String, Object> subData = new HashMap<>();
		subData.put("announce", Boolean.TRUE);
		subData.put("announce2", Boolean.TRUE);

		XWPFTemplate template = XWPFTemplate.compile("D:/repos/github/parent/test/src/test/resources/mybid/template.docx").render(
				new HashMap<String, Object>(){{
					put("nested", Includes.ofLocal("D:/repos/github/parent/test/src/test/resources/mybid/template1.docx").setRenderModel(subData).create());
					put("nested2", Includes.ofLocal("D:/repos/github/parent/test/src/test/resources/mybid/template2.docx").setRenderModel(subData).create());
				}}
		);
		try {

			template.writeAndClose(new FileOutputStream("D:/repos/github/parent/test/src/test/resources/mybid/output.docx"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

}

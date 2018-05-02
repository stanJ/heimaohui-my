/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.base.util;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author nijie
 *
 */
public class Crawler {

	// public static spider(){
	// Spider.create()
	// .scheduler()
	// .pipeline(new FilePipeline())
	// .thread(10).run();
	// }

	public class GithubRepoPageProcessor implements PageProcessor {

		private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

		@Override
		public void process(Page page) {
			System.out.println("start process page...");
			page.addTargetRequests(page.getHtml().links()
					.regex("(https://github\\.com/\\w+/\\w+)").all());
			page.putField("author",
					page.getUrl().regex("https://github\\.com/(\\w+)/.*")
							.toString());
			page.putField(
					"name",
					page.getHtml()
							.xpath("//h1[@class='entry-title public']/strong/a/text()")
							.toString());
			if (page.getResultItems().get("name") == null) {
				// skip this page
				page.setSkip(true);
			}
			page.putField("readme",
					page.getHtml().xpath("//div[@id='readme']/tidyText()"));
			System.out.println("end process page...");
		}

		@Override
		public Site getSite() {
			return site;
		}

	}

	public static void main(String[] args) {
		Spider.create(new Crawler().new GithubRepoPageProcessor())
				.addUrl("https://github.com/code4craft").thread(5).run();
	}
}

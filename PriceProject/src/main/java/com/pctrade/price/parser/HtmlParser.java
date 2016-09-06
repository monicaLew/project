package com.pctrade.price.parser;

import java.net.SocketTimeoutException;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.pctrade.price.entity.Car;

public class HtmlParser {

	public static Car extractCarInfo(String urlBase, Integer idPage) {
		Car car = new Car();
		try {
			StringBuilder s = new StringBuilder(urlBase);
			s.append(idPage);
			String url = s.toString();
			Document doc = Jsoup.connect(url).get();

			Elements divWithCarCost = doc.select("span.cost-eur");
			String carCostEuroS = divWithCarCost.last().text().replaceAll(" ", "");
			Integer carCostEuro = Integer.parseInt(carCostEuroS.replaceAll("€", ""));
			Elements divWithCarCostByr = doc.select("span.cost-byr");
			String carCostByr = divWithCarCostByr.last().text().replaceAll(" ", "");
			Double carCostBy = Double.parseDouble(carCostByr.replaceAll("р.", ""));

			Elements divStrong = doc.select("strong");
			String annonceId = divStrong.first().text().replaceAll("\u00a0", "").trim();
			Integer i = Integer.parseInt(annonceId);

			String link = doc.select("a.see_link_mod").text();
			Elements el = doc.select("div[style=font-weight:bold;font-size:1.4em;]");
			Integer year = Integer.parseInt(el.text().substring(0, 4));

			car.setPageCode(i);
			car.setArticle(link);
			car.setPriceByn(carCostBy);
			car.setPriceEuro(carCostEuro);
			car.setYear(year);
			car.setStatus("AVAILABLE");

		} catch (HttpStatusException e) { // надо ли созд новый или в выше
											// созданный car ?
			car.setPageCode(idPage);
			car.setArticle(null);
			car.setPriceByn(0.0);
			car.setPriceEuro(0);
			car.setYear(0);
			car.setStatus("PAGE_NOT_EXIST");
			return car;
		} catch (SocketTimeoutException ex) {
			car.setPageCode(idPage);
			car.setArticle(null);
			car.setPriceByn(0.0);
			car.setPriceEuro(0);
			car.setYear(0);
			car.setStatus("NOT_AVAILABLE_SOME_INET_PROBLEM");
			return car;
		} catch (NumberFormatException ex) {
			car.setPageCode(idPage);
			car.setArticle(null);
			car.setPriceByn(0.0);
			car.setPriceEuro(0);
			car.setYear(0);
			car.setStatus("PAGE_NOT_VALID");
			return car;
		} catch (NullPointerException e) {
			car.setPageCode(idPage);
			car.setArticle("NULL");
			car.setPriceByn(0.0);
			car.setPriceEuro(0);
			car.setYear(0);
			car.setStatus("PAGE_NOT_VALID");
			return car;
		} catch (Exception ex) {
			System.out.println("***** ******  ****  *****" + ex.getMessage());
			return null;
		}
		return car;
	}
}

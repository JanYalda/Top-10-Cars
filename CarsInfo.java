
/**
 * Jan Yalda
 * N01037094
 * CENG320 - Lab6
 * Date Created: 2016/12/06
 * Date Modified: 2017/05/30
 */

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.LinkedList;

public class CarsInfo {
	static Document webPage = null;

	/*
	 * @param url - the link of the WebSite to be probed This method will probe
	 * the URL and get HTML document back
	 */
	private void probeAction(String url) {
		try {
			String docURL = url;
			System.out.println("Probing: " + docURL);
			// connect to the URL
			Connection c = Jsoup.connect(docURL);
			// I don't need the header but just kept it anyways
			c.header("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:50.0) Gecko/20100101 Firefox/50.0");
			c.header("HTTP_ACCEPT", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			c.header("HTTP_ACCEPT_LANGUAGE", "en-US,en;q=0.5");
			c.header("HTTP_ACCEPT_ENCODING", "gzip, deflate");
			c.header("REQUEST_METHOD", "GET");
			// getting the HTML webPage
			webPage = c.get();

		} catch (IOException ex) {
			Logger.getLogger(CarsInfo.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static void main(String[] args) throws IOException, BadLocationException {
		CarsInfo carsInfo = new CarsInfo();
		// Printing the Edmunds part
		carsInfo.printEdmunds();
		// Printing the ConsumerReports part
		carsInfo.printConsRep();
	}

	/*
	 * This method handles the Edmunds WebSite
	 */
	public void printEdmunds() {
		// three lists to store the data in
		LinkedList<String> images = new LinkedList<>();
		LinkedList<String> names = new LinkedList<>();
		LinkedList<String> desc = new LinkedList<>();
		// Probing the URL
		probeAction("https://www.edmunds.com/car-reviews/top-10/10-best-affordable-road-trip-cars.html");
		// getting the elements using the class name
		Elements ele = webPage.getElementsByClass("module pod tid gutter-top-4 grid-146");

		for (Element element : ele) {
			// for each element found check if it has an <img> tag
			Elements all = element.getElementsByTag("img");
			for (Element element2 : all) {
				// putting the result in a string
				String e = element2.toString();
				// trimming the string to get the URL part
				int startIndex = e.indexOf("src=") + 7;
				int end = e.indexOf("width=") - 2;
				String srcTag = e.substring(startIndex, end);
				// had to take out the amp; so i replaced
				String str = srcTag.replace("amp;", "");
				// adding it to the list
				images.add("http://" + str);
			}
		}
		// printing the whole list of images URL's out
		for (String img : images) {
			System.out.println(img);
		}

		// getting the elements using the class name
		Elements ele2 = webPage.getElementsByClass("module pod tid gutter-top-4 grid-146");
		int i = 0;
		for (Element element : ele2) {
			// for each element found check if it has an <a> tag
			Elements all = element.getElementsByTag("a");

			for (Element element2 : all) {
				// skipping the first two results and anything after 11th result
				if (i < 2) {

				} else if (i > 11) {

				} else {
					// trimming the string to get the name of the car
					String e = element2.toString();
					int startIndex = e.indexOf("\">") + 2;
					int end = e.indexOf("</a>");
					String srcTag = e.substring(startIndex, end);
					// adding it to the list
					names.add(srcTag);
				}
				i++;
			}
		}
		// printing the whole cars names list out
		for (String name : names) {
			System.out.println(name);
		}

		// getting the elements using the class name
		Elements ele3 = webPage.getElementsByClass("module pod tid gutter-top-4 grid-146");
		int w = 0;
		int count = 0;
		for (Element element : ele3) {
			// for each element found check if it has an <p> tag
			Elements all = element.getElementsByTag("p");
			for (Element element2 : all) {
				// saving the result in a string
				String e = element2.toString();
				count++;
				// skipping the first 5 and anything after 46 from the results
				if (count > 5) {
					if (count < 46) {
						/*
						 * w helps me print concatinate the two paragraph
						 * belonging to car's description together
						 */
						if (w < 2) {
							// trimming the string to get the description
							e = element2.toString();
							int startIndex = e.indexOf("<p>") + 3;
							int end = e.indexOf("</p>");
							String srcTag = e.substring(startIndex, end);
							// adding it to the list
							desc.add(srcTag);
							if (w == 1) {
								// if
								desc.add("\n");
							}
							w++;
						} else {
							w++;
						}
					}
				}
				if (w == 4) {
					w = 0;
				}
			}
		}
		// priting the all the descprtions out
		for (String de : desc) {
			System.out.println(de);
		}
	}

	/*
	 * This method handles the ConsumerReports WebSite
	 */
	public void printConsRep() {
		// Three lists to store the data in
		LinkedList<String> images = new LinkedList<>();
		LinkedList<String> names = new LinkedList<>();
		LinkedList<String> desc = new LinkedList<>();
		// probing the URL
		probeAction("http://www.consumerreports.org/cars/top-cars-in-consumer-reports-road-tests/");
		// getting the elements using class name
		Elements ele = webPage.getElementsByClass("parsys main-content");
		int i = 0;
		for (Element element : ele) {
			// getting all elements with <img> tags
			Elements all = element.getElementsByTag("img");
			for (Element element2 : all) {
				// skip any result after 10
				if (i < 10) {
					// trimming the string using substring method
					String e = element2.toString();
					int startIndex = e.indexOf("src=") + 5;
					int end = e.indexOf("alt=") - 2;
					String srcTag = e.substring(startIndex, end);
					// adding the url to the list
					images.add(srcTag);
				}
				i++;
			}
		}
		// printing the list of image URL's out
		for (String img : images) {
			System.out.println(img);
		}

		// getting the elements using class name
		Elements ele2 = webPage.getElementsByClass("parsys main-content");
		int w = 0;
		for (Element element : ele2) {
			// getting all elements with <h2> tags
			Elements all = element.getElementsByTag("h2");
			for (Element element2 : all) {
				// skipping any results after the 10th one
				if (w < 10) {
					// trimming the string to get the name of car
					String e = element2.toString();
					int startIndex = e.indexOf("large\">") + 7;
					int end = e.indexOf("</h2>");
					String srcTag = e.substring(startIndex, end);
					// adding it to the list
					names.add(srcTag);
				}
				w++;
			}
		}
		// print all the car names
		for (String name : names) {
			System.out.println(name);
		}

		// getting the elements using class name
		Elements ele3 = webPage.getElementsByClass("parbase section text");
		int q = 0;
		for (Element element : ele3) {
			// getting all elements with <p> tags
			Elements all = element.getElementsByTag("p");
			for (Element element2 : all) {
				// trimming the result
				String e = element2.toString();
				// desc.add(e);
				// int startIndex = e.indexOf("<h2>") + 7;
				int startIndex = e.indexOf("<p>");
				int end = e.indexOf("</p>");
				String srcTag = e.substring(startIndex, end);
				// skip every reuslt that starts with read or find
				if (srcTag.startsWith("<p><b>Find out")) {

				} else if (srcTag.startsWith("<p><b>Read")) {

				} else {
					// skip the first 4 and anything after 13
					if (q < 4) {

					} else if (q > 13) {

					} else {
						// trimming the result
						int startIndex2 = e.indexOf("<h2>") + 4;
						int end2 = e.length() - 9;
						String srcTag2 = e.substring(startIndex2, end2);
						// adding it to the list
						desc.add(srcTag2);
					}
					q++;
				}
			}
		}
		// printing the whole list
		for (String de : desc) {
			System.out.println(de);
			System.out.println("");
		}
	}
}

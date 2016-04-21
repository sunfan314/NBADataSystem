/**
 * @date 2014年5月6日
 * @author huangjie
 */
package net.nba.data.updater;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Main {
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-mvc.xml");
	}
}

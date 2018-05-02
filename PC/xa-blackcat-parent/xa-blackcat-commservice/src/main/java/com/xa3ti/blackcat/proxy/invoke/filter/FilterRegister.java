/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.proxy.invoke.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.proxy.invoke.Invocation;
import com.xa3ti.blackcat.proxy.invoke.Invoker;
import com.xa3ti.blackcat.proxy.invoke.MockInvocation;
import com.xa3ti.blackcat.proxy.invoke.MockInvoker;
import com.xa3ti.blackcat.proxy.invoke.Result;
import com.xa3ti.blackcat.proxy.invoke.ServiceInvoker;

/**
 * @author nijie
 *
 */

@Component
public class FilterRegister {

	private static List<Filter> filters = new ArrayList<Filter>();

	private static Boolean chainBuilded = false;

	private static Invoker first;

	public static void registerFilter(Filter filter) {
		filters.add(filter);
	}

	public static void unregisterFilter(Filter filter) {
		filters.remove(filter);
	}

	public static Filter[] getFilters() {
		Filter[] f = new Filter[filters.size()];

		Collections.sort(filters, new Comparator() {

			@Override
			public int compare(Object o1, Object o2) {
				Filter f1 = (Filter) o1;
				Filter f2 = (Filter) o2;
				return f1.getSort() - f2.getSort();
			}

		});

		int i = 0;
		for (Filter ff : filters) {
			f[i] = ff;
			i++;
		}

		return f;
	}

	public static Invoker buildFilterChain(final Invoker invoker) {
		synchronized (chainBuilded) {
			if (chainBuilded) {
				System.out.println("buildFilterChain chainBuilded");
				return first;
			} else {
				System.out.println("buildFilterChain NOT chainBuilded");
				ConcurrentFilter cf = new ConcurrentFilter(1);
				SecurityFilter sf = new SecurityFilter(2);
				ParamaterRefactor pr = new ParamaterRefactor(3);
				ProtocolFilter pf = new ProtocolFilter(4);
				UpdateLimitFilter uf = new UpdateLimitFilter(5);
				CreateFilter ccf = new CreateFilter(6);
				ModifyAnyFilter mf=new ModifyAnyFilter(7);
				
				
				FilterRegister.registerFilter(cf);
				FilterRegister.registerFilter(sf);
				FilterRegister.registerFilter(pr);
				FilterRegister.registerFilter(pf);
				FilterRegister.registerFilter(uf);
				FilterRegister.registerFilter(ccf);
				FilterRegister.registerFilter(mf);

				getFilters();// sort it

				Invoker last = invoker;
				if (filters.size() > 0) {
					for (int i = filters.size() - 1; i >= 0; i--) {
						final Filter filter = filters.get(i);
						System.out.println("buildFilterChain SORT--->"
								+ filter.getSort());
						System.out.println("buildFilterChain CLASS--->"
								+ filter.getClass().getSimpleName());
						final Invoker next = last;
						last = new Invoker() {

							public Class getInterface() {
								return invoker.getInterface();
							}

							public Result invoke(Invocation invocation)
									throws BusinessException {
								return filter.invoke(next, invocation);
							}

							@Override
							public String toString() {
								return invoker.toString();
							}
						};
					}
				}
				chainBuilded = true;
				first = last;
				return last;
			}
		}

	}

}

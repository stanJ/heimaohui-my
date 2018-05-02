package com.xa3ti.blackcat.base.util.expression;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;




public class RuleResolver {

	public static void main(String[] args) {
		try {
			
			
			String rule="(hospitalid like \"abc\" or name like \"efg\") and (status==1 and online==1)";
			
			parseRule(rule);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("exception： " + e);
		}
	}

	public static ParseTree parseRule(String rule) {
		try {
			InputStream stream = new ByteArrayInputStream(
					rule.getBytes(StandardCharsets.UTF_8));
			CharStream inputStream = new ANTLRInputStream(stream);
			TokenStream tokens = new CommonTokenStream(new RuleLexer(
					inputStream));
			RuleParser parser = new RuleParser(tokens);
			ParseTree tree = (ParseTree) parser.compilationUnit();
//			SearchFilterWrapper searchFilterWrapper=new SearchFilterWrapper();
			printTree(tree, 0);
//			visitTree(tree, 0,false,searchFilterWrapper);
//			SearchFilterWrapper sw=cut(searchFilterWrapper,0);
			
			
			
			return tree;

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("exception： " + e);
		}
		return null;

	}
	
//	private static SearchFilterWrapper cut(SearchFilterWrapper searchFilterWrapper,int depth){
//		SearchFilterWrapper sw=new SearchFilterWrapper();
//		for(int i=0;i<depth;i++)
//			System.out.print(" ");
//		System.out.println(depth);
//		if(searchFilterWrapper!=null){
//			List<SearchFilterWrapper> childrens=searchFilterWrapper.getChildren();
//			SearchFilterWrapper sibling=searchFilterWrapper.getSibling();
//			if(searchFilterWrapper.getLink()!=null){
//				
//				sw.setSearchFilter(searchFilterWrapper.getSearchFilter());
//				sw.setLink(searchFilterWrapper.getLink());
//				if(sibling!=null){
//					   sw.setSibling(sibling);
//				}
//				if(childrens==null||childrens.size()==0){
//					return sw;
//				}
//			}
//			
//			if(childrens!=null&&childrens.size()>0){
//				for(SearchFilterWrapper child:childrens){
//					SearchFilterWrapper cutted=cut(child,depth+1);
//					if(cutted!=null){
//					    sw.addChild(cutted);
//					}
//				}
//				
//				return sw;
//			}
//			else{
//				return null;
//			}
//	}else
//		return null;
//	}

	public static Boolean checkRule(String rule) {
		try {
			InputStream stream = new ByteArrayInputStream(
					rule.getBytes(StandardCharsets.UTF_8));
			CharStream inputStream = new ANTLRInputStream(stream);
			TokenStream tokens = new CommonTokenStream(new RuleLexer(
					inputStream));
			RuleParser parser = new RuleParser(tokens);
			ParseTree tree = (ParseTree) parser.compilationUnit();
			return printTree(tree, 0);

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("exception： " + e);
			return false;
		}

	}

	
//
//	private static Object visitTree(ParseTree tree, int depth,boolean preFetch,SearchFilterWrapper searchFilterWrapper) {
//		String text=tree.getText();
//		
//		for(int i=0;i<depth;i++){
//		  System.out.print(" ");
//		}
//		System.out.print(depth+"->"+text);
//		System.out.println();
//		
//		
//		int n = tree.getChildCount();
//		if (n > 0) {
//			searchFilterWrapper.setType(searchFilterWrapper.TYPE_NOTATOM);
//			for (int i = 0; i < n; i++) {
//				ParseTree child = (ParseTree) tree.getChild(i);
//				ParseTree prefetchChild = (ParseTree) tree.getChild(i+1);
//				
//				
//				String firstChildText=child.getText();
//				if("or".equals(firstChildText)||"OR".equals(firstChildText)||"and".equals(firstChildText)||"AND".equals(firstChildText)){
//					searchFilterWrapper.setLink(firstChildText);
//					SearchFilterWrapper wrapper=new SearchFilterWrapper();
//					visitTree(prefetchChild, depth+1,false,wrapper); 
//					searchFilterWrapper.setSibling(wrapper);
//					return null;
//				}else if(!preFetch&&!text.contains("(")&&text.equals(firstChildText)){
//					String field=null;
//					Operator op=null;
//					Object v=null;
//					
//					field=firstChildText;	
//					searchFilterWrapper.setType(searchFilterWrapper.TYPE_ATOM);
//					
//					String opp=child.getChild(1).getText();
//					String vv=child.getChild(2).getText();
//				    op=convertToOpeartor(opp);
//				    v=convertToValue(vv);
//				    SearchFilter searchFilter=new SearchFilter(field,op,v);
//				    searchFilterWrapper.setSearchFilter(searchFilter);
//				    return null;
////					if(child.getParent()!=null){
////						op=(Operator)visitTree(child,depth+1,true,searchFilterWrapper);
////						v=visitTree(child.getParent(),depth+1,true,searchFilterWrapper);
////						SearchFilter searchFilter=new SearchFilter(field,op,v);
////						
////						searchFilterWrapper.setSearchFilter(searchFilter);
////					}
//				}else if(preFetch){
//						if(i==1){
//							String operator=child.getText();
//							Operator opp=convertToOpeartor(operator);
//						    return opp;
//						}else if(i==2){
//							String value=child.getText();
//							Object vv=convertToValue(value);
//						    return vv;
//						}
//					
//				}else{
//					if(text.contains("(")&&!"(".equals(text)){
//						SearchFilterWrapper wrapper=new SearchFilterWrapper();
//						 visitTree(child, depth+1,false,wrapper); 
//						 searchFilterWrapper.addChild(wrapper);
//						 
//					}else {
//						
//						if("(".equals(text)){
//				
//						SearchFilterWrapper wrapper=new SearchFilterWrapper();
//						visitTree(prefetchChild, depth+1,false,wrapper); 
//						searchFilterWrapper.addChild(wrapper);
//						return null;
//					   }
//						else{
//							if(i%2==0){
//								if(i==0){
//							       visitTree(child, depth+1,false,searchFilterWrapper);
//								}
//								else if(i>0){
//									SearchFilterWrapper wrapper=new SearchFilterWrapper();
//									visitTree(child, depth+1,false,wrapper); 
//									searchFilterWrapper.setSibling(wrapper);
//								}
//							}
//							else {
//								searchFilterWrapper.setLink(text);
//	//							SearchFilterWrapper wrapper=new SearchFilterWrapper();
//	//							visitTree(prefetchChild, depth+1,false,wrapper); 
//	//							searchFilterWrapper.setSibling(wrapper);
//							}
//						}
//					}
//				}
//				
//				
//				
//				ParseTree p=child.getParent().getParent();
//				
//			}
//			return text;
//		} else {
//			Object v = tree.getText();
////			if (String.valueOf(v).matches("[a-zA-Z0-9_]+")
////					&& !String.valueOf(v).matches("[0-9]+.?[0-9]*")) {
////				String methodName = String.valueOf(v).substring(0, 1)
////						.toUpperCase()
////						+ String.valueOf(v).substring(1);
////				v = callMethodOnObject(o, "get" + methodName, null);
////
////				if (String.valueOf(v).matches("[a-zA-Z0-9_]+")
////						&& !String.valueOf(v).matches("[0-9]+.?[0-9]*"))
////					v = "\"" + String.valueOf(v) + "\"";
////
////			}
//			
//			
//			return v;
//		}
//	}
	
	private static Object convertToValue(String value){
		return null;
	}
	
//	private static Operator convertToOpeartor(String op){
//		return null;
//	}

	private static boolean printTree(ParseTree tree, int depth) {
		int n = tree.getChildCount();
		boolean result=true;
		if (n > 0) {
			for (int i = 0; i < n; i++) {
				ParseTree child = (ParseTree) tree.getChild(i);
				
				for (int j = 0; j < depth; j++)
					System.out.print(" ");
				System.out.print(depth + "->" + child.getText());
				System.out.print("\n");

				
				if(child.getText().contains("missing"))
					return false;
				
				
				result=result&&printTree(child, depth + 1);

			}
		} else {
			return result;
		}
		return result;
	}

	private static Object callMethodOnObject(Object o, String methodName,
			Object... parameterValues) {
		Class[] c = null;
		if (parameterValues != null && parameterValues.length > 0) {
			c = new Class[parameterValues.length];
			for (int i = 0; i < parameterValues.length; i++) {
				c[i] = parameterValues[i].getClass();
			}
		}
		java.lang.reflect.Method m;
		try {
			m = o.getClass().getMethod(methodName, c);
			Object v = m.invoke(o, parameterValues);
			return v;
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

}

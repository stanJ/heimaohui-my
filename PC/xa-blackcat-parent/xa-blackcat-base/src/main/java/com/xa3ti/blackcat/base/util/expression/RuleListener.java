// Generated from Rule.g4 by ANTLR 4.5
package com.xa3ti.blackcat.base.util.expression;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link RuleParser}.
 */
public interface RuleListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link RuleParser#compilationUnit}.
	 * @param ctx the parse tree
	 */
	void enterCompilationUnit(RuleParser.CompilationUnitContext ctx);
	/**
	 * Exit a parse tree produced by {@link RuleParser#compilationUnit}.
	 * @param ctx the parse tree
	 */
	void exitCompilationUnit(RuleParser.CompilationUnitContext ctx);
	/**
	 * Enter a parse tree produced by {@link RuleParser#qualifiedName}.
	 * @param ctx the parse tree
	 */
	void enterQualifiedName(RuleParser.QualifiedNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link RuleParser#qualifiedName}.
	 * @param ctx the parse tree
	 */
	void exitQualifiedName(RuleParser.QualifiedNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link RuleParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(RuleParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link RuleParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(RuleParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link RuleParser#literalString}.
	 * @param ctx the parse tree
	 */
	void enterLiteralString(RuleParser.LiteralStringContext ctx);
	/**
	 * Exit a parse tree produced by {@link RuleParser#literalString}.
	 * @param ctx the parse tree
	 */
	void exitLiteralString(RuleParser.LiteralStringContext ctx);
	/**
	 * Enter a parse tree produced by {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(RuleParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(RuleParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RuleParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(RuleParser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link RuleParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(RuleParser.PrimaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link RuleParser#basic}.
	 * @param ctx the parse tree
	 */
	void enterBasic(RuleParser.BasicContext ctx);
	/**
	 * Exit a parse tree produced by {@link RuleParser#basic}.
	 * @param ctx the parse tree
	 */
	void exitBasic(RuleParser.BasicContext ctx);
}
<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:db="http://ananas.org/2002/docbook/subset">
	<xsl:output method="html"/>
	<xsl:template match="article">
		<html>
			<head>
				<title>
					<xsl:value-of select="articleinfo/title"/>
				</title>
			</head>
			<body>
				<xsl:apply-templates/>
			</body>
		</html>
	</xsl:template>
	<xsl:template match="simpara">
		<p>
			<xsl:apply-templates/>
		</p>
	</xsl:template>
	<xsl:template match="image">
		<image src="{@src}"/>
	</xsl:template>
	<xsl:template match="ulink">
		<a href="{@url}">
			<xsl:apply-templates/>
		</a>
	</xsl:template>
	<xsl:template match="copyright">
		<xsl:text>&#169; </xsl:text>
		<xsl:value-of select="year[1]"/>
		<xsl:if test="count(year) > 1">
			<xsl:text>-</xsl:text>
			<xsl:value-of select="year[position() = last()]"/>
		</xsl:if>
		<xsl:text>,</xsl:text>
		<xsl:apply-templates select="holder"/>
		<xsl:text>. All rights reserved.</xsl:text>
	</xsl:template>
	<xsl:template match="articleinfo/title">
		<h1>
			<xsl:apply-templates/>
		</h1>
	</xsl:template>
	<xsl:template match="title">
		<h2>
			<xsl:apply-templates/>
		</h2>
	</xsl:template>
</xsl:stylesheet>

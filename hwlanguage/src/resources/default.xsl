<?xml version="1.0"?>
<!-- this is a sample stylesheet created XM Eclipse Plugin, see ananas.org/xm -->
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:db="http://ananas.org/2002/docbook/subset">

<xsl:output method="html"/>

<xsl:template match="db:article">
   <html>
      <head><title><xsl:value-of select="db:articleinfo/db:title"/></title></head>
      <body>
         <xsl:apply-templates/>
      </body>
   </html>
</xsl:template>

<xsl:template match="db:simpara">
   <p><xsl:apply-templates/></p>
</xsl:template>

<xsl:template match="db:ulink">
   <a href="{@url}"><xsl:apply-templates/></a>
</xsl:template>

<xsl:template match="db:copyright">
   <xsl:text>&#169; </xsl:text>
   <xsl:value-of select="db:year[1]"/>
   <xsl:if test="count(db:year) > 1">
      <xsl:text>-</xsl:text>
      <xsl:value-of select="db:year[position() = last()]"/>
   </xsl:if>
   <xsl:text>, </xsl:text>
   <xsl:apply-templates select="db:holder"/>
   <xsl:text>. All rights reserved.</xsl:text>
</xsl:template>

<xsl:template match="db:articleinfo/db:title">
   <h1><xsl:apply-templates/></h1>
</xsl:template>

<xsl:template match="db:title">
   <h2><xsl:apply-templates/></h2>
</xsl:template>

</xsl:stylesheet>
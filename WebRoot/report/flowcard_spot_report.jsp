<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html xmlns:o="urn:schemas-microsoft-com:office:office"
    xmlns:w="urn:schemas-microsoft-com:office:word"
    xmlns="http://www.w3.org/TR/REC-html40">

    <head>
        <meta http-equiv=Content-Type content="text/html; charset=utf-8">
  
        <style>
<!--
 /* Font Definitions */
 @font-face
    {font-family:宋体;
    panose-1:2 1 6 0 3 1 1 1 1 1;
    mso-font-alt:SimSun;
    mso-font-charset:134;
    mso-generic-font-family:auto;
    mso-font-pitch:variable;
    mso-font-signature:3 135135232 16 0 262145 0;}
@font-face
    {font-family:"\@宋体";
    panose-1:2 1 6 0 3 1 1 1 1 1;
    mso-font-charset:134;
    mso-generic-font-family:auto;
    mso-font-pitch:variable;
    mso-font-signature:3 135135232 16 0 262145 0;}
 /* Style Definitions */
 p.MsoNormal, li.MsoNormal, div.MsoNormal
    {mso-style-parent:"";
    margin:0cm;
    margin-bottom:.0001pt;
    text-align:justify;
    text-justify:inter-ideograph;
    mso-pagination:none;
    font-size:10.5pt;
    mso-bidi-font-size:12.0pt;
    font-family:"Times New Roman";
    mso-fareast-font-family:宋体;
    mso-font-kerning:1.0pt;}
 /* Page Definitions */
 @page
    {mso-page-border-surround-header:no;
    mso-page-border-surround-footer:no;}
@page Section1
    {size:595.3pt 841.9pt;
    margin:72.0pt 90.0pt 72.0pt 90.0pt;
    mso-header-margin:42.55pt;
    mso-footer-margin:49.6pt;
    mso-paper-source:0;
    layout-grid:15.6pt;}
div.Section1
    {page:Section1;}
-->
</style>
 
    </head>

    <body lang=ZH-CN
        style='tab-interval:21.0pt;text-justify-trim:punctuation'>

        <div class=Section1 style='layout-grid:15.6pt'>

            <p class=MsoNormal align=center style='text-align:center'>
                <u><span lang=EN-US style='font-size:16.0pt'><span
                        style='mso-spacerun:yes'>&nbsp;&nbsp; </span>
                </span>
                </u><u><span
                    style='font-size:16.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";
mso-hansi-font-family:"Times New Roman"'>流向单抽查报告</span>
                </u><u><span lang=EN-US style='font-size:16.0pt'><span
                        style='mso-spacerun:yes'>&nbsp;&nbsp; </span>
                    <o:p></o:p>
                </span>
                </u>
            </p>

            <div align=center>

                <table class=MsoTableGrid border=1 cellspacing=0 cellpadding=0
                    style='border-collapse:collapse;border:none;mso-border-alt:solid windowtext .5pt;
 mso-yfti-tbllook:480;mso-padding-alt:0cm 5.4pt 0cm 5.4pt;mso-border-insideh:
 .5pt solid windowtext;mso-border-insidev:.5pt solid windowtext'>
                    <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes'>
                        <td width=142 valign=top
                            style='width:106.5pt;border:solid windowtext 1.0pt;
  mso-border-alt:solid windowtext .5pt;background:#E0E0E0;padding:0cm 5.4pt 0cm 5.4pt'>
                            <p class=MsoNormal align=center style='text-align:center'>
                                <b style='mso-bidi-font-weight:normal'><span
                                    style='font-family:宋体;mso-ascii-font-family:
  "Times New Roman";mso-hansi-font-family:"Times New Roman"'>级别</span><span
                                    lang=EN-US><o:p></o:p>
                                </span>
                                </b>
                            </p>
                        </td>
                        <td width=142 valign=top
                            style='width:106.5pt;border:solid windowtext 1.0pt;
  border-left:none;mso-border-left-alt:solid windowtext .5pt;mso-border-alt:
  solid windowtext .5pt;background:#E0E0E0;padding:0cm 5.4pt 0cm 5.4pt'>
                            <p class=MsoNormal align=center style='text-align:center'>
                                <b style='mso-bidi-font-weight:normal'><span
                                    style='font-family:宋体;mso-ascii-font-family:
  "Times New Roman";mso-hansi-font-family:"Times New Roman"'>分销商总数</span><span
                                    lang=EN-US><o:p></o:p>
                                </span>
                                </b>
                            </p>
                        </td>
                        
                                <td width=142 valign=top
                            style='width:106.5pt;border:solid windowtext 1.0pt;
  border-left:none;mso-border-left-alt:solid windowtext .5pt;mso-border-alt:
  solid windowtext .5pt;background:#E0E0E0;padding:0cm 5.4pt 0cm 5.4pt'>
                            <p class=MsoNormal align=center style='text-align:center'>
                                <b style='mso-bidi-font-weight:normal'><span
                                    style='font-family:宋体;mso-ascii-font-family:
  "Times New Roman";mso-hansi-font-family:"Times New Roman"'>会计核算期</span><span
                                    lang=EN-US><o:p></o:p>
                                </span>
                                </b>
                            </p>
                        </td>
                        <td width=142 valign=top
                            style='width:106.55pt;border:solid windowtext 1.0pt;
  border-left:none;mso-border-left-alt:solid windowtext .5pt;mso-border-alt:
  solid windowtext .5pt;background:#E0E0E0;padding:0cm 5.4pt 0cm 5.4pt'>
                            <p class=MsoNormal align=center style='text-align:center'>
                                <b style='mso-bidi-font-weight:normal'><span
                                    style='font-family:宋体;mso-ascii-font-family:
  "Times New Roman";mso-hansi-font-family:"Times New Roman"'>抽查总数</span><span
                                    lang=EN-US><o:p></o:p>
                                </span>
                                </b>
                            </p>
                        </td>
                        <td width=142 valign=top
                            style='width:106.55pt;border:solid windowtext 1.0pt;
  border-left:none;mso-border-left-alt:solid windowtext .5pt;mso-border-alt:
  solid windowtext .5pt;background:#E0E0E0;padding:0cm 5.4pt 0cm 5.4pt'>
                            <p class=MsoNormal align=center style='text-align:center'>
                                <b style='mso-bidi-font-weight:normal'><span
                                    style='font-family:宋体;mso-ascii-font-family:
  "Times New Roman";mso-hansi-font-family:"Times New Roman"'>百分比</span><span
                                    lang=EN-US><o:p></o:p>
                                </span>
                                </b>
                            </p>
                        </td>
                    </tr>
                 <s:iterator value="#request.distribTypes" var='dt'  status="st" >
                    <tr style='mso-yfti-irow:1'>
                        <td  align="center" width=142 valign=top
                            style='width:106.5pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
                            <p class=MsoNormal>
                                <span
                                    style='font-family:宋体;mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman"'><s:property value="#dt.name"  /></span>
                            </p>
                        </td>
                        <td   align="center"  width=142 valign=top
                            style='width:106.5pt;border-top:none;vertical-align: middle;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
                            <p class=MsoNormal> 
                                <span
                                    style='font-family:宋体;mso-ascii-font-family:"Times New Roman";
                                    
  mso-hansi-font-family:"Times New Roman"'><s:property value="#request.distribCounts[#st.index]"  /></span>
                            </p>
                        </td>
                               <td width=142 valign=top
                          >
								<table       >
								<s:iterator value="#request.fps"  var="fp" >
									<tr   >
									   <td width=142  align="center" valign=top 
                            style='width:106.55pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 0.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
								  <s:property value="#fp.year"  />-<s:property value="#fp.month"  /> 		
											</td>
									</tr></s:iterator>
								</table>
							</td>
                               <td width=142        >
								<table  >
								
								<s:iterator value="#request.spottedDistribMaps[#dt]"  var="spottedDistribCount" >
									<tr   >
									   <td width=142  align="center" valign=top 
                            style='width:106.55pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 0.5pt;border-right:solid windowtext 0.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
							 	
										<s:property value="#spottedDistribCount" />	</td>
									</tr></s:iterator>
								</table>
							</td>
							  <td width=142          >
								<table  cellpadding=0   cellspacing=0  >
								
								<s:iterator value="#request.spottedDistribMaps[#dt]"  var="spottedDistribCount" >
									<tr   >
									   <td width=142  align="center" valign=top 
                            style='width:106.55pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 0.5pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
							 	 
                                <span    style='font-family:宋体;mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman"'>	<s:property value="#spottedDistribCount" />/<s:property value="#request.distribCounts[#st.index]"  /> </span>  	</td>
                          
									
									</tr></s:iterator>
								</table>
							</td>
                   
                    </tr>
             </s:iterator>
                </table>

            </div>

            <p class=MsoNormal>
                <span lang=EN-US><o:p>&nbsp;</o:p>
                </span>
            </p>

        </div>
<s:debug></s:debug>
    </body>

</html>


package org.randomcoder.taglibs.input;

import org.randomcoder.taglibs.common.HtmlHelper;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Base class of all tags which produce &lt;input&gt; tags.
 *
 * <pre>
 * Copyright (c) 2006, Craig Condit. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * </pre>
 */
abstract public class InputTagBase extends TagSupport
    implements ScriptableInputTagAttributes {
  private static final long serialVersionUID = 4169511989162728785L;

  private final Map<String, String> params = new HashMap<String, String>();

  private String styleId;
  private String name;
  private String value;

  /**
   * Sets the name HTML attribute.
   *
   * @param name value of name attribute
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Sets the value HTML attribute.
   *
   * @param value value of value attribute
   */
  public void setValue(String value) {
    this.value = value;
  }

  /**
   * Sets the styleId (HTML style) attribute.
   *
   * @param styleId value of styleId attribute
   */
  public void setStyleId(String styleId) {
    this.styleId = styleId;
  }

  /**
   * Gets the value HTML attribute.
   *
   * @return value attribute value
   */
  protected String getValue() {
    return value;
  }

  /**
   * Gets the name HTML attribute.
   *
   * @return name attribute value
   */
  protected String getName() {
    return name;
  }

  /**
   * Gets the styleId (HTML style) attribute.
   *
   * @return styleId attribute value
   */
  protected String getStyleId() {
    return styleId;
  }

  /**
   * Gets additional parameters associated with this tag.
   *
   * @return parameter map
   */
  protected Map<String, String> getParams() {
    return params;
  }

  // attributes common to all INPUT tags

  @Override public void setDisabled(String disabled) {
    boolean dis = false;
    if ("true".equalsIgnoreCase(disabled))
      dis = true;
    if ("1".equalsIgnoreCase(disabled))
      dis = true;
    if ("disabled".equalsIgnoreCase(disabled))
      dis = true;

    if (dis)
      params.put("disabled", "disabled");
    else
      params.remove("disabled");
  }

  @Override public void setReadonly(String readonly) {
    boolean ro = false;
    if ("true".equalsIgnoreCase(readonly))
      ro = true;
    if ("1".equalsIgnoreCase(readonly))
      ro = true;
    if ("readonly".equalsIgnoreCase(readonly))
      ro = true;

    if (ro)
      params.put("readonly", "readonly");
    else
      params.remove("readonly");
  }

  @Override public void setTabindex(String tabindex) {
    params.put("tabindex", tabindex);
  }

  @Override public void setAccesskey(String accesskey) {
    params.put("accesskey", accesskey);
  }

  // attributes common to all HTML tags

  @Override public void setStyleClass(String styleClass) {
    params.put("class", styleClass);
  }

  @Override public void setDir(String dir) {
    params.put("dir", dir);
  }

  @Override public void setLang(String lang) {
    params.put("lang", lang);
  }

  @Override public void setStyle(String style) {
    params.put("style", style);
  }

  @Override public void setTitle(String title) {
    params.put("title", title);
  }

  // scripting attributes common to all INPUT tags

  @Override public void setOnblur(String onblur) {
    params.put("onblur", onblur);
  }

  @Override public void setOnchange(String onchange) {
    params.put("onchange", onchange);
  }

  @Override public void setOnfocus(String onfocus) {
    params.put("onfocus", onfocus);
  }

  @Override public void setOnselect(String onselect) {
    params.put("onselect", onselect);
  }

  // scripting attributes common to all HTML tags

  @Override public void setOnclick(String onclick) {
    params.put("onclick", onclick);
  }

  @Override public void setOndblclick(String ondblclick) {
    params.put("ondblclick", ondblclick);
  }

  @Override public void setOnkeydown(String onkeydown) {
    params.put("onkeydown", onkeydown);
  }

  @Override public void setOnkeypress(String onkeypress) {
    params.put("onkeypress", onkeypress);
  }

  @Override public void setOnkeyup(String onkeyup) {
    params.put("onkeyup", onkeyup);
  }

  @Override public void setOnmousedown(String onmousedown) {
    params.put("onmousedown", onmousedown);
  }

  @Override public void setOnmousemove(String onmousemove) {
    params.put("onmousemove", onmousemove);
  }

  @Override public void setOnmouseout(String onmouseout) {
    params.put("onmouseout", onmouseout);
  }

  @Override public void setOnmouseover(String onmouseover) {
    params.put("onmouseover", onmouseover);
  }

  @Override public void setOnmouseup(String onmouseup) {
    params.put("onmouseup", onmouseup);
  }

  /**
   * Release state.
   */
  @Override public void release() {
    params.clear();
    name = null;
    value = null;
    styleId = null;
  }

  /**
   * Renders this tag to the output.
   *
   * @return EVAL_PAGE
   */
  @Override public int doEndTag() throws JspException {
    try {
      JspWriter out = pageContext.getOut();

      out.write("<input type=\"" + getType() + "\"");
      if (name != null)
        out.write(" name=\"" + HtmlHelper.encodeAttribute(name) + "\"");
      if (styleId != null)
        out.write(" id=\"" + HtmlHelper.encodeAttribute(styleId) + "\"");
      if (value != null)
        out.write(" value=\"" + HtmlHelper.encodeAttribute(value) + "\"");
      out.write(buildOptions());
      out.write(" />");
    } catch (IOException ioe) {
      throw new JspException(ioe);
    }

    return EVAL_PAGE;
  }

  /**
   * Renders attribute values.
   *
   * @return String holding attributes
   */
  protected String buildOptions() {
    StringBuilder buf = new StringBuilder();

    for (String key : params.keySet()) {
      String val = params.get(key);
      if (val != null) {
        buf.append(" ");
        buf.append(key);
        buf.append("=\"");
        buf.append(HtmlHelper.encodeAttribute(val));
        buf.append("\"");
      }
    }

    return buf.toString();
  }

  /**
   * Gets the value of the 'type' attribute used in final tag.
   *
   * @return type attribute value
   */
  abstract protected String getType();
}

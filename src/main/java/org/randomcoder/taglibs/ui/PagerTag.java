package org.randomcoder.taglibs.ui;

import org.randomcoder.taglibs.common.HtmlHelper;
import org.randomcoder.taglibs.common.RequestHelper;
import org.randomcoder.taglibs.common.UrlHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Tag class which implements a pager control.
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
public class PagerTag extends TagSupport {
  private static final long serialVersionUID = -2171165306736367599L;

  private static final String DEFAULT_LIMIT_PARAM = "limit";
  private static final String DEFAULT_START_PARAM = "start";
  private static final String DEFAULT_PAGE_PARAM = "page";

  private static final String DEFAULT_PREV_CONTENT = "&#171;";
  private static final String DEFAULT_NEXT_CONTENT = "&#187;";

  private static final int DEFAULT_MAX_LINKS = 10;

  private PageContext pageContext = null;

  private String limitParam = DEFAULT_LIMIT_PARAM;
  private String startParam = DEFAULT_START_PARAM;
  private String pageParam = DEFAULT_PAGE_PARAM;

  private String link = null;
  private String prevContent = DEFAULT_PREV_CONTENT;
  private String nextContent = DEFAULT_NEXT_CONTENT;

  private Integer page;
  private Integer start;
  private int count = 0;
  private int limit = 0;
  private int pageOffset = 0;
  private int startOffset = 0;

  private int maxLinks = DEFAULT_MAX_LINKS;

  /**
   * Sets the total number of items in this view.
   *
   * @param count item count
   */
  public void setCount(int count) {
    this.count = count;
  }

  /**
   * Sets the number of items per page in this view.
   *
   * @param limit page limit
   */
  public void setLimit(int limit) {
    this.limit = limit;
  }

  /**
   * Sets the parameter name used for page limit (defaults to 'limit').
   *
   * @param limitParam parameter name
   */
  public void setLimitParam(String limitParam) {
    this.limitParam = limitParam;
  }

  /**
   * Sets the URL to use when generating pager links (defaults to current page).
   *
   * @param link url
   */
  public void setLink(String link) {
    this.link = link;
  }

  /**
   * Sets the maximum number of links to render on each side of current
   * (defaults to 10).
   *
   * @param maxLinks number of links to render
   */
  public void setMaxLinks(int maxLinks) {
    this.maxLinks = maxLinks;
  }

  /**
   * Sets the HTML content to use for the next link (defaults to a right angle
   * quote).
   *
   * @param nextContent HTML content to render as next link
   */
  public void setNextContent(String nextContent) {
    this.nextContent = nextContent;
  }

  /**
   * Sets the HTML content to use for the previous link (defaults to a left
   * angle quote).
   *
   * @param prevContent HTML content to render as previous link
   */
  public void setPrevContent(String prevContent) {
    this.prevContent = prevContent;
  }

  /**
   * Sets the starting item number (from 0).
   *
   * @param start starting item number
   */
  public void setStart(int start) {
    this.start = start;
  }

  /**
   * Sets the offset for the start parameter in generated links (defaults to 0).
   *
   * @param startOffset start offset
   */
  public void setStartOffset(int startOffset) {
    this.startOffset = startOffset;
  }

  /**
   * Sets the starting page number (from 0).
   *
   * @param page starting page number
   */
  public void setPage(int page) {
    this.page = page;
  }

  /**
   * Sets the offset for the page parameter in generated links (defaults to 0).
   *
   * @param pageOffset page offset
   */
  public void setPageOffset(int pageOffset) {
    this.pageOffset = pageOffset;
  }

  /**
   * Sets the parameter name used for the page (defaults to 'page').
   *
   * @param pageParam parameter name
   */
  public void setPageParam(String pageParam) {
    this.pageParam = pageParam;
  }

  /**
   * Sets the parameter name used for the first item (defaults to 'start').
   *
   * @param startParam parameter name
   */
  public void setStartParam(String startParam) {
    this.startParam = startParam;
  }

  /**
   * Sets the JSP PageContext variable.
   */
  @Override public void setPageContext(PageContext pageContext) {
    super.setPageContext(pageContext);
    this.pageContext = pageContext;
  }

  /**
   * Release state.
   */
  @Override public void release() {
    super.release();
    cleanup();
  }

  private void cleanup() {
    limitParam = DEFAULT_LIMIT_PARAM;
    startParam = DEFAULT_START_PARAM;
    pageParam = DEFAULT_PAGE_PARAM;
    maxLinks = DEFAULT_MAX_LINKS;
    prevContent = DEFAULT_PREV_CONTENT;
    nextContent = DEFAULT_NEXT_CONTENT;
    count = 0;
    limit = 0;
    start = null;
    page = null;
    link = null;
    pageOffset = 0;
    startOffset = 0;
    pageContext = null;
  }

  /**
   * Renders the pager to the current page context's JSPWriter.
   *
   * @return EVAL_PAGE
   */
  @Override public int doEndTag() throws JspException {
    try {
      DecimalFormat df = new DecimalFormat("####################");

      JspWriter out = pageContext.getOut();

      // make sure values are sane
      if (limit < 1) {
        limit = 10;
      }

      // figure out which mode we're operating in (start or page)
      if (start == null && page == null) {
        throw new JspException(
            "Either start or page must be specified on pager tag");
      }

      boolean pageMode = (start == null);

      if (pageMode) {
        if (page < 0) {
          page = 0;
        }
      } else {
        if (start < 0) {
          start = 0;
        }
      }

      // calculate page count
      int pageCount = (int) Math.ceil((double) count / (double) limit);

      // calculate current page
      int currentPage;
      if (pageMode) {
        currentPage = page;
      } else {
        currentPage = (int) Math.floor((double) start / (double) limit);
      }

      // special case - we're past end, so adjust accordingly
      if (currentPage >= pageCount) {
        currentPage = pageCount++;
      }

      if (currentPage > 0) {
        renderLink(out, pageMode, (currentPage - 1), (currentPage - 1) * limit,
            limit, prevContent);
        out.print(" ");
      }

      // output prev links
      int first = Math.max(0, currentPage - maxLinks);
      for (int i = first; i < currentPage; i++) {
        renderLink(out, pageMode, i, i * limit, limit, df.format(i + 1));
        out.print(" ");
      }

      // output current page
      out.print(df.format(currentPage + 1));

      // output next links
      int last = Math.min(pageCount, currentPage + maxLinks);
      for (int i = currentPage + 1; i < last; i++) {
        out.print(" ");
        renderLink(out, pageMode, i, i * limit, limit, df.format(i + 1));
      }

      if (currentPage + 1 < pageCount) {
        out.print(" ");
        renderLink(out, pageMode, currentPage + 1, (currentPage + 1) * limit,
            limit, nextContent);
      }

      return EVAL_PAGE;

    } catch (IOException e) {
      throw new JspException(e);
    } finally {
      cleanup();
    }
  }

  private void renderLink(JspWriter out, boolean pageMode, int linkPage,
      int linkStart, int linkLimit, String text) throws IOException {
    HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
    HttpServletResponse response =
        (HttpServletResponse) pageContext.getResponse();

    DecimalFormat df = new DecimalFormat("####################");

    URL targetURL = null;
    String output = null;
    boolean relative = false;

    URL currentPage = RequestHelper.getCurrentUrl(request);

    if (link == null) {
      // no link specified, use current URL
      targetURL = currentPage;
      relative = true;
    } else {
      targetURL = new URL(currentPage, link);
      relative = UrlHelper.isUrlRelative(targetURL, currentPage);
    }

    Map<String, String> params = new HashMap<String, String>();
    if (pageMode) {
      params.put(pageParam, df.format(linkPage + pageOffset));
    } else {
      params.put(startParam, df.format(linkStart + startOffset));
    }
    params.put(limitParam, df.format(linkLimit));
    URL generated = RequestHelper.appendParameters(request, targetURL, params);

    if (relative) {
      output = response.encodeURL(generated.getFile());
    } else {
      output = response.encodeURL(generated.toExternalForm());
    }

    // write it out
    out.print("<a href=\"");
    out.print(HtmlHelper.encodeAttribute(output));
    out.print("\"");
    out.print(">");
    out.print(text);
    out.print("</a>");
  }

}

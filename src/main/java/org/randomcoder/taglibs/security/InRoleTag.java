package org.randomcoder.taglibs.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * Tag class which implements isUserInRole for JSP files.
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
public class InRoleTag extends BodyTagSupport {
  private static final long serialVersionUID = 3339945981150007004L;

  private String role;

  /**
   * Sets the role to test
   *
   * @param role role name
   */
  public void setRole(String role) {
    this.role = role;
  }

  /**
   * Determines whether to process body content.
   *
   * @return SKIP_BODY if user is not in role(s), EVAL_BODY_INCLUDE otherwise
   */
  @Override public int doStartTag() throws JspException {
    HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

    String[] roles = role.split(",");
    for (int i = 0; i < roles.length; i++) {
      if (request.isUserInRole(roles[i].trim()))
        return EVAL_BODY_INCLUDE;
    }

    return SKIP_BODY;
  }
}

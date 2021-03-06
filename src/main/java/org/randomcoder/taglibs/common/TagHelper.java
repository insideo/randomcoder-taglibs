package org.randomcoder.taglibs.common;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import java.util.Locale;

/**
 * Helper class containing common tag functionality.
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
public class TagHelper {
  /**
   * Gets the PageContext scope associated with the given name.
   *
   * @param scopeName scope to lookup
   * @return PageContext scope
   * @throws JspException if scopeName is invalid
   */
  public static int getScope(String scopeName) throws JspException {
    if (scopeName == null)
      throw new JspException("scope is null");

    String test = scopeName.toUpperCase(Locale.US);

    if ("PAGE".equals(test))
      return PageContext.PAGE_SCOPE;
    if ("REQUEST".equals(test))
      return PageContext.REQUEST_SCOPE;
    if ("SESSION".equals(test))
      return PageContext.SESSION_SCOPE;
    if ("APPLICATION".equals(test))
      return PageContext.APPLICATION_SCOPE;
    throw new JspException("Invalid scope: " + scopeName);
  }
}

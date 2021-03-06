package org.randomcoder.taglibs.input;

import org.randomcoder.taglibs.test.base.AbstractTagTestCase;

@SuppressWarnings("javadoc") public class SubmitTagTest
    extends AbstractTagTestCase {
  private SubmitTag tag;

  @Override protected void setUp() throws Exception {
    tag = new SubmitTag();
    setUp(tag);
  }

  @Override protected void tearDown() throws Exception {
    tag.release();
    tag = null;
    super.tearDown();
  }

  public void testDoEndTag() throws Exception {
    tag.doEndTag();
    String result = writer.getBuffer().toString();
    assertTrue("bad name", result.contains(" type=\"submit\""));
  }
}

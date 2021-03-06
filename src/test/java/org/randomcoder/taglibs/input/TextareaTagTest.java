package org.randomcoder.taglibs.input;

import org.randomcoder.taglibs.test.base.AbstractTagTestCase;

@SuppressWarnings("javadoc") public class TextareaTagTest
    extends AbstractTagTestCase {
  private TextareaTag tag;

  @Override protected void setUp() throws Exception {
    tag = new TextareaTag();
    setUp(tag);
  }

  @Override protected void tearDown() throws Exception {
    tag.release();
    tag = null;
    super.tearDown();
  }

  public void testDoEndTag() throws Exception {
    tag.setRows("10");
    tag.setCols("80");
    tag.setName("name");
    tag.setStyleId("id");
    tag.setValue("value");
    tag.doEndTag();
    String result = writer.getBuffer().toString();
    assertTrue("bad tag name", result.contains("<textarea"));
    assertTrue("bad name", result.contains(" name=\"name\""));
    assertTrue("bad id", result.contains(" id=\"id\""));
    assertTrue("bad rows", result.contains(" rows=\"10\""));
    assertTrue("bad cols", result.contains(" cols=\"80\""));
    assertTrue("bad value", result.contains(">value<"));
  }
}

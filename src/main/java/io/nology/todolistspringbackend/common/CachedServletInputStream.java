package io.nology.todolistspringbackend.common;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

// import java.util.logging.Logger;
// import org.apache.commons.io.IOUtils;
// import org.hibernate.validator.internal.util.logging.LoggerFactory;

public class CachedServletInputStream extends ServletInputStream {

  private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(
    CachedServletInputStream.class
  );
  private InputStream cachedInputStream;

  public CachedServletInputStream(byte[] cachedBody) {
    this.cachedInputStream = new ByteArrayInputStream(cachedBody);
  }

  @Override
  public boolean isFinished() {
    try {
      return cachedInputStream.available() == 0;
    } catch (IOException exp) {
      LOGGER.error(exp.getMessage());
    }
    return false;
  }

  @Override
  public boolean isReady() {
    return true;
  }

  @Override
  public void setReadListener(ReadListener readListener) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int read() throws IOException {
    return cachedInputStream.read();
  }
}

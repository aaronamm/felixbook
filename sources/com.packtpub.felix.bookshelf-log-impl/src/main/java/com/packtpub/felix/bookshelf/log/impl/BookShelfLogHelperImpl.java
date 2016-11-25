package com.packtpub.felix.bookshelf.log.impl;
import com.packtpub.felix.bookshelf.log.api.BookShelfLogHelper;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.osgi.service.log.LogService;
import java.text.MessageFormat;


@Component(name = "BookShelfLogHelperImpl",immediate = true)
@Provides
@Instantiate(name = "BookShelfLogHelperImplInstance")
public class BookShelfLogHelperImpl implements BookShelfLogHelper {

    @Requires
    LogService log;

    public void debug(String pattern, Object... args) {
        String message = MessageFormat.format(pattern, args);
        this.log.log(LogService.LOG_DEBUG, message);
    }

    public void debug(String pattern, Throwable throwable, Object... args) {
        String message = MessageFormat.format(pattern, args);
        this.log.log(LogService.LOG_DEBUG,message,throwable);
    }
}

package xyz.codingmentor.interceptor;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 *
 * @author Ákos
 */
@Interceptor
public class LoggerInterceptor {
    @AroundInvoke
    public Object invoke(InvocationContext invocationContext) throws Exception {
        
        final Logger LOGGER = Logger.getLogger("Method called: ");
        String name = invocationContext.getMethod().getName();
        
        LOGGER.log(Level.INFO, "Method was called, name: {0}", name+".");
        return invocationContext.proceed();
    }
}

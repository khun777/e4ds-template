package ch.ralscha.e4ds.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import ch.ralscha.extdirectspring.bean.ExtDirectResponseBuilder;

@Component
@Lazy
public class ExceptionHandler implements HandlerExceptionResolver {
	private final static Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {

		logger.error("error", ex);
		ExtDirectResponseBuilder.create(request, response).setException(ex).buildAndWrite();
		return null;

	}

}

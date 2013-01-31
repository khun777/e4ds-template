package ch.rasc.e4ds.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
// @Component
public class AroundExtMethodsAspect {

	@Around("@annotation(ch.ralscha.extdirectspring.annotation.ExtDirectMethod)")
	public Object log(ProceedingJoinPoint pjp) throws Throwable {
		Object retVal = pjp.proceed();

		System.out.println(pjp);

		return retVal;
	}

	@Before("@annotation(ch.ralscha.extdirectspring.annotation.ExtDirectMethod)")
	public void before(JoinPoint jp) {
		System.out.println("Before: " + jp);
	}

	@After("@annotation(ch.ralscha.extdirectspring.annotation.ExtDirectMethod)")
	public void after(JoinPoint jp) {
		System.out.println("After: " + jp);
	}

}

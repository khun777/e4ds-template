package ch.rasc.e4ds.schedule;

import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ch.rasc.e4ds.entity.AccessLog;
import ch.rasc.e4ds.entity.QAccessLog;

import com.mysema.query.jpa.impl.JPAQuery;

@Component
public class AccessLogCleanup {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	@Scheduled(cron = "0 0 5 * * *")
	public void doCleanup() {
		// Delete all access logs that are older than 6 months
		LocalDateTime sixMonthAgo = LocalDateTime.now().minusMonths(6);

		for (AccessLog al : new JPAQuery(entityManager).from(QAccessLog.accessLog)
				.where(QAccessLog.accessLog.logIn.loe(sixMonthAgo))
				.list(QAccessLog.accessLog)) {
			entityManager.remove(al);
		}

	}

}

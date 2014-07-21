package ch.rasc.e4ds.schedule;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ch.rasc.e4ds.entity.LoggingEvent;
import ch.rasc.e4ds.entity.QLoggingEvent;

import com.mysema.query.jpa.impl.JPAQuery;

@Component
public class LogCleanup {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	@Scheduled(cron = "0 0 4 * * *")
	public void doCleanup() {

		// Delete all log entries that are older than 2 months
		long twoMonthsAgo = ZonedDateTime.now(ZoneOffset.UTC).minusMonths(2)
				.toEpochSecond() * 1000L;

		for (LoggingEvent le : new JPAQuery(entityManager)
				.from(QLoggingEvent.loggingEvent)
				.where(QLoggingEvent.loggingEvent.timestmp.loe(twoMonthsAgo))
				.list(QLoggingEvent.loggingEvent)) {
			entityManager.remove(le);
		}

	}
}

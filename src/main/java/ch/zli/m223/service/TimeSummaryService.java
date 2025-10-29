package ch.zli.m223.service;

import java.time.ZoneId;
import java.util.List;

import ch.zli.m223.model.Entry;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TimeSummaryService {

  public String calculateSummaryPerDay(List<Entry> entries) {
    ZoneId zone = ZoneId.systemDefault();
    long totalMillis = 0L;

    for (Entry entry : entries) {
      long diff = entry.getCheckOut().atZone(zone).toInstant().toEpochMilli()
        - entry.getCheckIn().atZone(zone).toInstant().toEpochMilli();
      totalMillis += diff;
    }

    long seconds = totalMillis / 1000;
    long hours   = seconds / 3600;
    long minutes = (seconds % 3600) / 60;
    long secs    = seconds % 60;

    return String.format("%02d:%02d:%02d", hours, minutes, secs);
  }

}

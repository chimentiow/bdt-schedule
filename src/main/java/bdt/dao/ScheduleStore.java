package bdt.dao;

import bdt.model.Schedule;
import bdt.model.ScheduleEntry;

/** Сховище тижневого розкладу. */
public interface ScheduleStore {
    Schedule loadSchedule();

    int insert(ScheduleEntry entry);

    void delete(int entryId);
}

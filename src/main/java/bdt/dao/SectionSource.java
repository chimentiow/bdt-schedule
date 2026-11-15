package bdt.dao;

import bdt.model.Section;
import java.util.List;

/** Джерело даних про секції (Indirection / Protected Variations): реалізується DAO або заглушкою. */
public interface SectionSource {
    Section findSection(int sectionId);

    int countActiveParticipants(int sectionId);

    List<Section> findAll();
}

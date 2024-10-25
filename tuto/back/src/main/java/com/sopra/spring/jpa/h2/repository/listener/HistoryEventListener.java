package com.sopra.spring.jpa.h2.repository.listener;

import com.sopra.spring.jpa.h2.model.Tutorial;
import com.sopra.spring.jpa.h2.service.HistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Log4j2
@Component
@RequiredArgsConstructor
public class HistoryEventListener implements PostInsertEventListener, PostUpdateEventListener {

    public static final List<String> FIELDS_IGNORED = Arrays.asList("histories", "version");
    private final transient EntityManagerFactory entityManagerFactory;

    private final transient HistoryService historyService;

    @PostConstruct
    private void init() {
        SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
        EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
        registry.getEventListenerGroup(EventType.POST_INSERT).appendListener(this);
        registry.getEventListenerGroup(EventType.POST_UPDATE).appendListener(this);
    }

    @Override
    public void onPostInsert(PostInsertEvent event) {
        if (event.getEntity() instanceof Tutorial) {
            Tutorial tutorial = (Tutorial) event.getEntity();
            String[] properties = event.getPersister().getPropertyNames();
            try {
                for (String property : properties) {
                    if (!FIELDS_IGNORED.contains(property)) {
                        String newValue = this.transform(PropertyUtils.getProperty(event.getEntity(), property));
                        String oldValue = null;
                        if (!Objects.equals(newValue, oldValue)) {
                            this.historyService.create(
                                    tutorial,
                                    property,
                                    oldValue,
                                    newValue);
                        }
                    }
                }
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                log.error("onPostInsert - erreur", e);
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void onPostUpdate(PostUpdateEvent event) {
        if (event.getEntity() instanceof Tutorial) {
            Tutorial tutorial = (Tutorial) event.getEntity();
            String[] properties = event.getPersister().getPropertyNames();
            try {
                for (int i : event.getDirtyProperties()) {
                    if (!this.isEquals(event.getOldState()[i], event.getState()[i])) {
                        String property = properties[i];
                        if (!FIELDS_IGNORED.contains(property)) {
                            String oldValue = this.transform(event.getOldState()[i]);
                            String newValue = this.transform(PropertyUtils.getProperty(event.getEntity(), property));
                            if (!Objects.equals(newValue, oldValue)) {
                                this.historyService.create(
                                        tutorial,
                                        property,
                                        oldValue,
                                        newValue);
                            }
                        }
                    }
                }
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                log.error("vPostUpdateEvent - erreur", e);
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister entityPersister) {
        // Empty OK - method not used
        return false;
    }

    private String transform(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof Boolean) {
            return (Boolean) value ? "oui" : "non";
        }
        return value.toString();
    }

    private boolean isEquals(Object o1, Object o2) {
        return (o1 == null && o2 == null) || (o1 != null && o1.equals(o2));
    }
}

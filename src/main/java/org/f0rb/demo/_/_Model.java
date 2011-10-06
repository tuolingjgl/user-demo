package org.f0rb.demo._;

import java.text.MessageFormat;
import java.util.*;

/**
 * Class org.f0rb.demo._ description goes here.
 *
 * @author Administrator
 * @version 1.0.0 11-10-2
 */
public abstract class _Model {
    /** 分页查询时的起始位置. */
    private Integer start;
    /** 分页查询时的最大条数. */
    private Integer limit;
    /** 查询条件对应记录的总条数 */
    private Long total;
    private List list;
    private String serviceName;
    private Map<String, List<String>> messages;

    public synchronized void setMessages(Map<String, List<String>> messages) {
        this.messages = messages;
    }

    public synchronized Map<String, List<String>> getMessages() {
        return hasMessages() ? new LinkedHashMap<String, List<String>>(internalGetMessages()) : null;
    }

    public synchronized void addMessageKey(String fieldName, String key) {
        addMessage(fieldName, text(key));
    }

    public synchronized void addMessage(String fieldName, String message) {
        final Map<String, List<String>> messages = internalGetMessages();
        List<String> filedMessages = messages.get(fieldName);

        if (filedMessages == null) {
            filedMessages = new ArrayList<String>();
            messages.put(fieldName, filedMessages);
        }

        filedMessages.add(message);
    }

    public synchronized boolean hasMessages() {
        return (messages != null) && !messages.isEmpty();
    }

    private Map<String, List<String>> internalGetMessages() {
        if (messages == null) {
            messages = new LinkedHashMap<String, List<String>>();
        }
        return messages;
    }

    public synchronized void clearMessages() {
        internalGetMessages().clear();
    }

    private static ResourceBundle lang = null;

    public static String text(String key, Object o) {
        return new MessageFormat(text(key)).format(o);
    }

    public static String text(String key) {
        if (lang == null) {
            try {
                lang = ResourceBundle.getBundle("lang", new Locale("zh", "CN"));
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }
        return key == null || !lang.containsKey(key) ? "" : lang.getString(key);
    }

    public void setLocale(String s) {
        String[] arr = s.split("_");
        Locale locale;
        try {
            locale = new Locale(arr[0], arr[1]);
            lang = ResourceBundle.getBundle("lang", locale);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                lang = ResourceBundle.getBundle("lang", new Locale("zh", "CN"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Method getStart returns the start of this _DTO object.
     *
     * @return the start (type Integer) of this _DTO object.
     */
    public Integer getStart() {
        return start;
    }

    /**
     * Method setStart sets the start of this _DTO object.
     *
     * @param start the start of this _DTO object.
     */
    public void setStart(String start) {
        try {
            this.start = Integer.valueOf(start);
        } catch (NumberFormatException e) {
            this.start = 0;
        }
    }

    /**
     * Method getLimit returns the limit of this _DTO object.
     *
     * @return the limit (type Integer) of this _DTO object.
     */
    public Integer getLimit() {
        return limit;
    }

    /**
     * Method setLimit sets the limit of this _DTO object.
     *
     * @param limit the limit of this _DTO object.
     */
    public void setLimit(String limit) {
        try {
            this.limit = Integer.valueOf(limit);
        } catch (NumberFormatException e) {
            this.limit = 0;
        }
    }

    /**
     * Method getTotal returns the total of this _DTO object.
     *
     * @return the total (type Long) of this _DTO object.
     */
    public Long getTotal() {
        return total;
    }

    /**
     * Method setTotal sets the total of this _DTO object.
     *
     * @param total the total of this _DTO object.
     */
    public void setTotal(Long total) {
        this.total = total;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}

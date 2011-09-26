package org.f0rb.demo._;


import java.text.MessageFormat;
import java.util.*;

/**
 * Class _DTO ...
 *
 * @author f0rb
 * @version 1.0.0 2011-1-2
 */
public abstract class _DTO<T> {
    /** 分页查询时的起始位置. */
    private Integer start;
    /** 分页查询时的最大条数. */
    private Integer limit;
    /** 查询条件对应记录的总条数 */
    private Long total;
    private Object[] index;
    private String entry0;
    private String entry1;
    private String entry2;
    private String indexBy;
    private String indexFor;
    private String serviceName;
    private Map<String, List<String>> messages;

    public abstract T toPojo();

    public abstract void fillByPojo(T t);

    public abstract void fillInPojo(T t);

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
     * Method getEntry1 returns the entry1 of this _DTO object.
     *
     * @return the entry1 (type String) of this _DTO object.
     */
    public String getEntry1() {
        return entry1;
    }

    /**
     * Method setEntry1 sets the entry1 of this _DTO object.
     *
     * @param entry1 the entry1 of this _DTO object.
     */
    public void setEntry1(String entry1) {
        this.entry1 = entry1;
    }

    /**
     * Method getEntry2 returns the entry2 of this _DTO object.
     *
     * @return the entry2 (type String) of this _DTO object.
     */
    public String getEntry2() {
        return entry2;
    }

    /**
     * Method setEntry2 sets the entry2 of this _DTO object.
     *
     * @param entry2 the entry2 of this _DTO object.
     */
    public void setEntry2(String entry2) {
        this.entry2 = entry2;
    }

    /**
     * Method getIndexBy returns the indexBy of this _DTO object.
     *
     * @return the indexBy (type String) of this _DTO object.
     */
    public String getIndexBy() {
        return indexBy;
    }

    /**
     * Method setIndexBy sets the indexBy of this _DTO object.
     *
     * @param indexBy the indexBy of this _DTO object.
     */
    public void setIndexBy(String indexBy) {
        this.indexBy = indexBy;
    }

    /**
     * Method getEntry0 returns the entry0 of this _DTO object.
     *
     * @return the entry0 (type String) of this _DTO object.
     */
    public String getEntry0() {
        return entry0;
    }

    /**
     * Method setEntry0 sets the entry0 of this _DTO object.
     *
     * @param entry0 the entry0 of this _DTO object.
     */
    public void setEntry0(String entry0) {
        this.entry0 = entry0;
    }

    /**
     * Method getIndexFor returns the indexFor of this _DTO object.
     *
     * @return the indexFor (type String) of this _DTO object.
     */
    public String getIndexFor() {
        return indexFor;
    }

    /**
     * Method setIndexFor sets the indexFor of this _DTO object.
     *
     * @param indexFor the indexFor of this _DTO object.
     */
    public void setIndexFor(String indexFor) {
        this.indexFor = indexFor;
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

    /**
     * Method getIndex returns the index of this _DTO object.
     *
     * @return the index (type Object[]) of this _DTO object.
     */
    public Object[] getIndex() {
        return index;
    }

    /**
     * Method setIndex sets the index of this _DTO object.
     *
     * @param index the index of this _DTO object.
     */
    public void setIndex(Object[] index) {
        this.index = index;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}

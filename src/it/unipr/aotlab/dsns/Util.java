/* Copyright */

package it.unipr.aotlab.dsns;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * User: enrico
 * Package: it.unipr.aotlab.dsns
 * Date: 9/9/11
 * Time: 10:43 AM
 */
public class Util {
    static String show(Object o) {
        return Show.getShow(o).toString();
    }

    static private class Show implements Map<String, Show> {
        static private class NULLShow extends Show {
            NULLShow() {
            }

            public String toString() {
                return "<null>";
            }
        }

        static private Show NULL_SHOW = new NULLShow();

        Map<String, Show> delegate = new HashMap();

        private Show() {
        }

        private Show(Object o) {
            discoverFields(o);
        }

        static Show getShow(Object o) {
            if (o == null) {
                return NULL_SHOW;
            } else {
                return new Show(o);
            }
        }

        private void discoverFields(Object o) {
            Class<Object> cl = (Class<Object>) o.getClass();
            Field[] fields = cl.getDeclaredFields();
            for (Field field : fields) {
                Object val = null;
                try {
                    val = field.get(o);
                    delegate.put(
                            field.toGenericString(),
                            getShow(o));
                } catch (IllegalAccessException e) {
                    // ok to pass
                }
            }
        }

        @Override
        public int size() {
            return delegate.size();
        }

        @Override
        public boolean isEmpty() {
            return delegate.isEmpty();
        }

        @Override
        public boolean containsKey(final Object o) {
            return delegate.containsKey(o);
        }

        @Override
        public boolean containsValue(final Object o) {
            return delegate.containsValue(o);
        }

        @Override
        public Show get(final Object o) {
            return delegate.get(o);
        }

        @Override
        public Show put(final String s, final Show show) {
            return delegate.put(s, show);
        }

        @Override
        public Show remove(final Object o) {
            return delegate.remove(o);
        }

        @Override
        public void putAll(final Map<? extends String, ? extends Show> map) {
            delegate.putAll(map);
        }

        @Override
        public void clear() {
            delegate.clear();
        }

        @Override
        public Set<String> keySet() {
            return delegate.keySet();
        }

        @Override
        public Collection<Show> values() {
            return delegate.values();
        }

        @Override
        public Set<Entry<String, Show>> entrySet() {
            return delegate.entrySet();
        }

        @Override
        public boolean equals(final Object o) {
            return delegate.equals(o);
        }

        @Override
        public int hashCode() {
            return delegate.hashCode();
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("Show");
            sb.append("{").append(delegate);
            sb.append('}');
            return sb.toString();
        }
    }
}

package addit.vutbr.fit.addit.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * This class represents one task in database, it also implements Observable to allow automatic changes in view via databinding
 */
public class Task extends RealmObject implements Observable, RealmDataBinding {

    @PrimaryKey
    private int primaryKey;

    private String name;
    private String description;
    private Date date;
    private int hour;
    private int minute;
    private RealmList<Task> subtask;
    private String priority;
    private String state;

    private boolean isRoot = true;


    @Bindable
    public boolean getIsRoot(){
        return isRoot;
    }

    public void setIsRoot(boolean isRoot){
        this.isRoot = isRoot;
    }

    public int getPrimaryKey() {
        return primaryKey;
    }

    @Bindable
    public String getState(){
        return state;
    }

    public void setState(String state){
        this.state = state;
        notifyChange();
    }

    public State getStateAsEnum(){
        return State.valueOf(getState());
    }

    public void setState(State state){
        setState(state.toString());
    }

    @Bindable
    public String getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        setPriority(priority.toString());
    }

    public void setPriority(String pr) {
        this.priority = pr;
        notifyChange();
    }

    public Priority getPriorityEnumValue() {
        return Priority.valueOf(getPriority());
    }

    // simulating enum with private ignored field,
    // @see stackoverflow.com/questions/33464677/how-to-store-enums-in-realm

    public int getPriorityAsDrawable() {
        return Priority.valueOf(priority !=null ? priority : Priority.green.name()).getDrawable();
    }

    @Bindable
    public RealmList<Task> getSubtask() {
        return subtask;
    }

    public void setSubtask(RealmList<Task> subtask) {
        this.subtask = subtask;
        notifyChange();
    }

    @Bindable
    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
        notifyChange();
    }

    @Bindable
    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
        notifyChange();
    }

    @Bindable
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
        notifyChange();
    }

    @Bindable
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
        notifyChange();
    }

    @Bindable
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyChange();
    }


    public String getFormatedDate() {
        if(date != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd / MM / yyyy", Locale.US);
            return simpleDateFormat.format(date);
        } else
            return "unkown";
    }

    public String getFormatedTime() {
        return String.format("%02d", this.hour) + " : " + String.format("%02d", this.minute);
    }


    @Override
    public String toString() {
        return "Task{" +
                "priorityDrawable=" + getPriorityAsDrawable() +
                ", primaryKey=" + primaryKey +
                ", priority='" + priority + '\'' +
                ", subtask=" + subtask +
                ", date=" + date +
                ", hour=" + hour +
                ", minute=" + minute +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", state='" + state + '\'' +
                '}';
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////
    ////                Things down there are just copy past from BaseObservable
    @Ignore
    private transient PropertyChangeRegistry mCallbacks;

    @Override
    public synchronized void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        if (mCallbacks == null) {
            mCallbacks = new PropertyChangeRegistry();
        }
        mCallbacks.add(callback);
    }

    @Override
    public synchronized void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        if (mCallbacks != null) {
            mCallbacks.remove(callback);
        }
    }

    /**
     * Notifies listeners that all properties of this instance have changed.
     */
    public synchronized void notifyChange() {
        if (mCallbacks != null) {
            mCallbacks.notifyCallbacks(this, 0, null);
        }
    }

    /**
     * Notifies listeners that a specific property has changed. The getter for the property
     * that changes should be marked with {@link Bindable} to generate a field in
     * <code>BR</code> to be used as <code>fieldId</code>.
     *
     * @param fieldId The generated BR primaryKey for the Bindable field.
     */
    public void notifyPropertyChanged(int fieldId) {
        if (mCallbacks != null) {
            mCallbacks.notifyCallbacks(this, fieldId, null);
        }
    }

    public NonDtbTask createNonDTBCopy() {
        NonDtbTask task = new NonDtbTask();
        task.primaryKey = this.primaryKey;
        task.name = this.name;
        task.description = this.description;
        task.date = this.date;
        task.hour = this.hour;
        task.minute = this.minute;
        task.subtask = this.subtask;
        task.priority = this.priority;
        task.isRoot = this.isRoot;
        return task;
    }

    public static class NonDtbTask extends BaseObservable{
        @PrimaryKey
        private int primaryKey;

        private String name;
        private String description;
        private Date date;
        private int hour;
        private int minute;
        private RealmList<Task> subtask = new RealmList<>();
        private String priority;

        private boolean isRoot;

        @Bindable
        public boolean getIsRoot(){
            return isRoot;
        }

        public void setIsRoot(boolean isRoot){
            this.isRoot = isRoot;
        }

        @Bindable
        public int getPrimaryKey() {
            return primaryKey;
        }

        public void setPrimaryKey(int primaryKey) {
            this.primaryKey = primaryKey;
            notifyChange();
        }
        @Bindable
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
            notifyChange();
        }

        @Bindable
        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
            notifyChange();
        }

        @Bindable
        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
            notifyChange();
        }

        @Bindable
        public int getHour() {
            return hour;
        }

        public void setHour(int hour) {
            this.hour = hour;
            notifyChange();
        }

        @Bindable
        public int getMinute() {
            return minute;
        }

        public void setMinute(int minute) {
            this.minute = minute;
            notifyChange();
        }

        @Bindable
        public RealmList<Task> getSubtask() {
            return subtask;
        }

        public void setSubtask(RealmList<Task> subtask) {
            this.subtask = subtask;
            notifyChange();
        }

        @Bindable
        public String getPriority() {
            return priority;
        }

        public void setPriority(String priority) {
            this.priority = priority;
            notifyChange();
        }

        public int getPriorityAsDrawable() {
            return Priority.valueOf(priority).getDrawable();
        }


        @Override
        public String toString() {
            return "Task{" +
                    "primaryKey=" + primaryKey +
                    ", priority='" + priority + '\'' +
                    ", subtask=" + subtask +
                    ", date=" + date +
                    ", hour=" + hour +
                    ", minute=" + minute +
                    ", description='" + description + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }

        public String getFormatedDate() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd / MM / yyyy", Locale.US);
            return simpleDateFormat.format(date);
        }

        public String getFormatedTime() {
            return String.format("%02d", this.hour) + " : " + String.format("%02d", this.minute);
        }

    }
}
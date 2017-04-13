package addit.vutbr.fit.addit;

import java.util.Date;
import java.util.List;

import addit.vutbr.fit.addit.databinding.ActivityMainPageBinding;
import addit.vutbr.fit.addit.lib.RealmFind;
import addit.vutbr.fit.addit.model.Priority;
import addit.vutbr.fit.addit.model.RealmDataBinding;
import addit.vutbr.fit.addit.model.State;
import addit.vutbr.fit.addit.model.Task;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Brain of the whole application
 */
public class Controller {
    private static Controller instance;

    public static synchronized Controller getInstance() {
        if (instance == null)
            instance = new Controller();
        return instance;
    }

    /**
     * Construcs the controller, and add demo tasks into it
     */
    private Controller() {

        // todo remove the deleteAll when basic implementation is over
//        commitIntoRealm(realm -> realm.deleteAll());
//        insertDemoDataIntoRealm();
    }

    /**
     * Insert some demo data, so that the view has something to show
     */
    private void insertDemoDataIntoRealm() {
        Task task = new Task();
        task.setName("Name");
        task.setDescription("Description from controller");
        task.setPriority(Priority.red.name());
        task.setDate(new Date(1995,11,11));
        task.setHour(13);
        task.setMinute(55);
        createTaskFromBlueprint(task);
    }

    /**
     * method contains boilerplate code neccessary for access database and changing it
     * Whenever possible, use this method to chage content of realm
     */
    private void commitIntoRealm(Consumer<Realm> command) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        command.accept(realm);
        realm.commitTransaction();
    }

    /**
     * Returns all tasks from the database
     * @param state specifies which kind of tasks to fetch
     */
    public RealmResults<Task> getAllTasks(State state) {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Task.class).equalTo("state",state.toString()).findAll();
    }

    public RealmResults<Task> getAllTasks() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Task.class).findAll();
    }

    /**
     * Returns the firt task from the database (for testing purposes mainly)
     */
    public Task getMostImportantTask() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Task.class).equalTo("state",State.ACTIVE.toString()).equalTo("isRoot",true).findFirst();
    }

    /**
     * Inserts new task into the database
     *
     * @param task task to be added
     */
    public void addNewTask(Task task) {
        commitIntoRealm(realm -> realm.copyToRealm(task));
    }


    /**
     * Set the state of task as deleted
     * @param task
     */
    public void markTaskAsDeleted(Task task) {
        commitIntoRealm(realm -> task.setState(State.DELETED));
    }

    /**
     * Set the state of task as finished
     * @param task
     */
    public void markTaskAsFinished(Task task) {
        commitIntoRealm(realm -> task.setState(State.FINISHED));
    }

    /**
     * deletes task from database
     * @param task
     */
    public void deleteTask(Task task){
        commitIntoRealm(realm -> task.deleteFromRealm());
    }

    public void setMainActivityBinding(ActivityMainPageBinding binding, Task task) {
        //provide callback enabling auto synchronization when the database object changes
        task.addChangeListener(RealmDataBinding.FACTORY.create());

        // set the binding
        binding.setTask(task);
    }

    /**
     * creates new task in the database by the given blueprint
     * @param blueprint
     * @return
     */
    public Task createTaskFromBlueprint(Task blueprint) {
        Task res;
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        res = realm.createObject(blueprint.getClass(),RealmFind.nextPrimaryKey(realm,blueprint.getClass()));
        res.setName(blueprint.getName());
        res.setDescription(blueprint.getDescription());
        res.setPriority(blueprint.getPriority());
        res.setDate(blueprint.getDate());
        res.setSubtask(blueprint.getSubtask());
        res.setHour(blueprint.getHour());
        res.setMinute(blueprint.getMinute());
        realm.commitTransaction();
        return res;
    }

    /**
     * creates new task in the database by the given blueprint
     * @param blueprint
     * @return
     */
    public Task updateTask(Task.NonDtbTask blueprint) {
        Task res;
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        res = realm.where(Task.class).equalTo("primaryKey",blueprint.getPrimaryKey()).findFirst();
        res.setName(blueprint.getName());
        res.setDescription(blueprint.getDescription());
        res.setPriority(blueprint.getPriority());
        res.setDate(blueprint.getDate());
        //res.getSubtask().addAll(blueprint.getSubtask())
        res.setHour(blueprint.getHour());
        res.setMinute(blueprint.getMinute());
        res.setIsRoot(blueprint.getIsRoot());
        realm.commitTransaction();
        return res;
    }

    public Task updateTask(Task.NonDtbTask blueprint, List<Task> subtasks) {
        Task res;
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        res = realm.where(Task.class).equalTo("primaryKey",blueprint.getPrimaryKey()).findFirst();
        res.setName(blueprint.getName());
        res.setDescription(blueprint.getDescription());
        res.setPriority(blueprint.getPriority());
        res.setDate(blueprint.getDate());
        res.getSubtask().addAll(subtasks);
        res.setHour(blueprint.getHour());
        res.setIsRoot(blueprint.getIsRoot());
        res.setMinute(blueprint.getMinute());
        realm.commitTransaction();
        return res;
    }

    public void updateTaskDescription(Task task, String description) {
        commitIntoRealm(realm -> {
            task.setDescription(description);
        });
    }

    public Task createEmptyTask() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Task t = realm.createObject(Task.class,RealmFind.nextPrimaryKey(realm,Task.class));
        t.setState(State.ACTIVE);
        t.setSubtask(new RealmList<>());
        realm.commitTransaction();
        return t;
    }

    public void addSubtaskToTask(Task.NonDtbTask task, Task newTask) {
        Realm defaultInstance = Realm.getDefaultInstance();
        defaultInstance.beginTransaction();
        task.getSubtask().add(newTask);
        defaultInstance.commitTransaction();
    }




    /**
     * typical java 8 functional interface
     * @param <T>
     */
    interface Consumer<T> {
        void accept(T t);
    }
}

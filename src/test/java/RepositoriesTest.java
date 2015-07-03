import com.gitHub.xMIFx.domain.Department;
import com.gitHub.xMIFx.domain.Worker;
import com.gitHub.xMIFx.repositories.implementationDAO.collectionDAO.DepartmentCollectionDAOImpl;
import com.gitHub.xMIFx.repositories.implementationDAO.collectionDAO.WorkerCollectionDAOImpl;
import com.gitHub.xMIFx.repositories.interfacesDAO.DepartmentDAO;
import com.gitHub.xMIFx.repositories.interfacesDAO.WorkerDAO;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Vlad on 02.07.2015.
 */
public class RepositoriesTest {
    @Test
    public void testingWorkerDAO() {
        WorkerDAO workerDAO = new WorkerCollectionDAOImpl();
        Worker worker = new Worker("Vlad");
        workerDAO.save(worker);

        Worker readWorker = workerDAO.getByName("Vlad");
        Assert.assertEquals("not the same object after save-getByName",worker, readWorker); //test, it's work Ok need refactoring

        readWorker = workerDAO.getById(worker.getId());
        Assert.assertEquals("not the same object after save-getById",worker, readWorker);

        worker.setLogin("MIF");
        workerDAO.update(worker);

        readWorker = workerDAO.getByName("Vlad");
        Assert.assertEquals("not the same object after update-getByName",worker, readWorker);


        readWorker = workerDAO.getById(worker.getId());
        Assert.assertEquals("not the same object after update-getByID",worker, readWorker);

        workerDAO.remove(worker);

        readWorker = workerDAO.getByName("Vlad");
        Assert.assertEquals("remove don't work",null, readWorker);
    }

    @Test
    public void testingDepartmentDAO() {
        DepartmentDAO departmentDAO = new DepartmentCollectionDAOImpl();
        Department department = new Department("Vlad");
        departmentDAO.save(department);

        Department readDepartment = departmentDAO.getByName("Vlad");
        Assert.assertEquals("not the same object after save-getByName",department, readDepartment);

        readDepartment = departmentDAO.getById(department.getId());
        Assert.assertEquals("not the same object after save-getByID",department, readDepartment);

        department.addWorker(new Worker());
        departmentDAO.update(department);

        readDepartment = departmentDAO.getByName("Vlad");
        Assert.assertEquals("not the same object after update-getByName",department, readDepartment);

        readDepartment = departmentDAO.getById(department.getId());
        Assert.assertEquals("not the same object after update-getByID",department, readDepartment);

        departmentDAO.remove(department);

        readDepartment = departmentDAO.getByName("Vlad");
        Assert.assertEquals("remove don't work",null, readDepartment);

    }
}

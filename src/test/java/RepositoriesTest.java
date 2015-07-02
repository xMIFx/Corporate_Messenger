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
        if (!worker.equals(readWorker)) {
            Assert.fail("not the same object after save-getByName");
        } else {/*NOP*/}

        readWorker = workerDAO.getById(worker.getId());
        if (!worker.equals(readWorker)) {
            Assert.fail("not the same object after save-getById");
        } else {/*NOP*/}
        worker.setLogin("MIF");
        workerDAO.update(worker);

        readWorker = workerDAO.getByName("Vlad");
        if (!worker.equals(readWorker)) {
            Assert.fail("not the same object after update-getByName");
        } else {/*NOP*/}

        readWorker = workerDAO.getById(worker.getId());
        if (!worker.equals(readWorker)) {
            Assert.fail("not the same object after update-getByName");
        } else {/*NOP*/}
        workerDAO.remove(worker);
        readWorker = workerDAO.getByName("Vlad");
        if (readWorker != null) {
            Assert.fail("remove don't work");
        }
    }

    @Test
    public void testingDepartmentDAO() {
        DepartmentDAO departmentDAO = new DepartmentCollectionDAOImpl();
        Department department = new Department("Vlad");
        departmentDAO.save(department);

        Department readDepartment = departmentDAO.getByName("Vlad");
        if (!department.equals(readDepartment)) {
            Assert.fail("not the same object after save-getByName");
        } else {/*NOP*/}

        readDepartment = departmentDAO.getById(department.getId());
        if (!department.equals(readDepartment)) {
            Assert.fail("not the same object after save-getById");
        } else {/*NOP*/}
        department.addWorker(new Worker());
        departmentDAO.update(department);

        readDepartment = departmentDAO.getByName("Vlad");
        if (!department.equals(readDepartment)) {
            Assert.fail("not the same object after update-getByName");
        } else {/*NOP*/}

        readDepartment = departmentDAO.getById(department.getId());
        if (!department.equals(readDepartment)) {
            Assert.fail("not the same object after update-getByName");
        } else {/*NOP*/}
        departmentDAO.remove(department);
        readDepartment = departmentDAO.getByName("Vlad");
        if (readDepartment != null) {
            Assert.fail("remove don't work");
        }

    }
}

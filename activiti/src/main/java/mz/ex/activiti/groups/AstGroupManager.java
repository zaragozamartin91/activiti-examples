package mz.ex.activiti.groups;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.activiti.engine.impl.persistence.entity.GroupEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.data.GroupDataManager;
import org.springframework.stereotype.Service;

@Service
public class AstGroupManager implements GroupEntityManager {

	@Override
	public GroupEntity create() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GroupEntity findById(String entityId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(GroupEntity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void insert(GroupEntity entity, boolean fireCreateEvent) {
		// TODO Auto-generated method stub

	}

	@Override
	public GroupEntity update(GroupEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GroupEntity update(GroupEntity entity, boolean fireUpdateEvent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(GroupEntity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(GroupEntity entity, boolean fireDeleteEvent) {
		// TODO Auto-generated method stub

	}

	@Override
	public Group createNewGroup(String groupId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GroupQuery createNewGroupQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Group> findGroupByQueryCriteria(GroupQueryImpl query, Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long findGroupCountByQueryCriteria(GroupQueryImpl query) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Group> findGroupsByUser(String userId) {
		switch (userId) {
			case "foo":
				return Arrays.asList(new Group[] {
						new AstGroup("accountancy"),
						new AstGroup("route"),
				});
			case "bar":
				return Arrays.asList(new Group[] {
						new AstGroup("management"),
						new AstGroup("account"),
				});
			case "zee":
				return Arrays.asList(new Group[] {
						new AstGroup("manager"),
				});

			default:
				return Arrays.asList(new Group[] {

				});
		}
	}

	@Override
	public List<Group> findGroupsByNativeQuery(Map<String, Object> parameterMap, int firstResult, int maxResults) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long findGroupCountByNativeQuery(Map<String, Object> parameterMap) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isNewGroup(Group group) {
		// TODO Auto-generated method stub
		return false;
	}
}

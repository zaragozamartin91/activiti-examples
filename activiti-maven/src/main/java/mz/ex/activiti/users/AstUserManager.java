package mz.ex.activiti.users;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.Picture;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import org.springframework.stereotype.Service;

import mz.ex.activiti.groups.AstGroup;

@Service
public class AstUserManager implements UserEntityManager {

	@Override
	public UserEntity create() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserEntity findById(String entityId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(UserEntity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void insert(UserEntity entity, boolean fireCreateEvent) {
		// TODO Auto-generated method stub

	}

	@Override
	public UserEntity update(UserEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserEntity update(UserEntity entity, boolean fireUpdateEvent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(UserEntity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(UserEntity entity, boolean fireDeleteEvent) {
		// TODO Auto-generated method stub

	}

	@Override
	public User createNewUser(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateUser(User updatedUser) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<User> findUserByQueryCriteria(UserQueryImpl query, Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long findUserCountByQueryCriteria(UserQueryImpl query) {
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
	public UserQuery createNewUserQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean checkPassword(String userId, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findUsersByNativeQuery(Map<String, Object> parameterMap, int firstResult, int maxResults) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long findUserCountByNativeQuery(Map<String, Object> parameterMap) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isNewUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Picture getUserPicture(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUserPicture(String userId, Picture picture) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deletePicture(User user) {
		// TODO Auto-generated method stub

	}

}

package mz.ex.activiti.groups;

import org.activiti.engine.identity.Group;

public class AstGroup implements Group {
	private static final long serialVersionUID = 5982965428404172377L;

	private String id;
	private String name;
	private String type;

	public AstGroup(String id, String name, String type) {
		this.id = id;
		this.name = name;
		this.type = type;
	}

	public AstGroup(String id) {
		this(id, id, id);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}

package pt.ul.fc.css.democracia2.domain;

import java.util.Objects;

import org.springframework.lang.NonNull;

public class Topic {


	private Topic parent;
	public Topic getParent() {
		return parent;
	}

	private String name;

	public String getName() {
		return name;
	}

	public Topic(@NonNull String name, Topic parent) {
		this.name = name;
		this.parent = parent;
	}

	@Override
	public String toString() {
		return parent + ">"+name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, parent);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Topic other = (Topic) obj;
		return Objects.equals(name, other.name) && Objects.equals(parent, other.parent);
	}


}

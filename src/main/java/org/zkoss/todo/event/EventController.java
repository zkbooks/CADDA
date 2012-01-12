package org.zkoss.todo.event;

/**
 * Event Controller.
 * 
 * @author robbiecheng
 */

import java.util.List;
import java.util.UUID;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

public class EventController extends GenericForwardComposer {
	private static final long serialVersionUID = -9145887024839938515L;
	private TodoEvent current = new TodoEvent();
	private EventDAO eventDao = new EventDAO();
	private Textbox name;
	private Intbox priority;
	private Datebox date;

	public void onSelect$box(Event e) {
		ForwardEvent forwardEvt = (ForwardEvent) e;
		Listbox box = (Listbox) forwardEvt.getOrigin().getTarget();

		if (box.getSelectedItem() != null) {
			current = (TodoEvent) box.getSelectedItem().getValue();
			refresh();
		}
	}

	private void refresh() {
		name.setValue(current.getName());
		priority.setValue(current.getPriority());
		date.setValue(current.getDate());
	}

	public TodoEvent getCurrent() {
		return current;
	}

	public void setCurrent(TodoEvent current) {
		this.current = current;
	}

	public List<TodoEvent> getAllEvents() {
		return eventDao.findAll();
	}

	private boolean validate(TodoEvent current2) {
		if (current.getId() == null || current.getName() == null
				|| current.getName().length() <= 0 || current.getDate() == null) {

			Messagebox.show("All fields cannot be empty, please check fields.");

			return false;
		}

		return true;
	}

	public void onClick$add() {
		if (current != null) {
			current.setId(UUID.randomUUID().toString());

			if (validate(current)) {
				// insert into database
				eventDao.insert(current);
			}
		}
	}

	public void onClick$update() {
		if (current != null && validate(current)) {
			// update database
			eventDao.update(current);
		}
	}

	public void onClick$delete() {
		if (current != null && validate(current)) {
			eventDao.delete(current);
		}
	}
}

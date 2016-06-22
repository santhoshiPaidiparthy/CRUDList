package crudexample.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import crudexample.domain.Profile;
import crudexample.service.CRUDService;

@SuppressWarnings("rawtypes")
public class UserListContrroller extends GenericForwardComposer {

	@WireVariable
	private CRUDService CRUDService;

	private static final long serialVersionUID = 1L;

	protected Listbox UserListbox; // autowired
	protected Window userList; // autowired

	// Databinding
	private AnnotateDataBinder binder;
	private BindingListModelList<Profile> appUsersList;
	private Profile appUsers;
	private Profile selectedUser;

	public UserListContrroller() {
		super();
	}

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);
		this.self.setAttribute("controller", this, false);
	}

	public void onCreate$userList(Event event) throws Exception {

		this.binder = (AnnotateDataBinder) event.getTarget().getAttribute(
				"binder", true);

		setSelectedUser(null);
		doFillListbox();

		this.binder.loadAll();
	}

	public void doFillListbox() {

		CRUDService = (CRUDService) SpringUtil.getBean("CRUDService");
		List<Profile> list = CRUDService.getAll(Profile.class);
		appUsersList = new BindingListModelList<Profile>(list, true);
		//setAppUsersList(appUsersList);
		this.UserListbox.setModel(appUsersList);
	}

	/*This method will be called when user press the new button in the Listing screen*/
	 
	public void onClick$btnNew(Event event) {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("selectedRecord", null);
		map.put("recordMode", "NEW");
		map.put("parentWindow",userList);
		Executions.createComponents("UserCRUD.zul", null, map);
	}
	
	public void onEdit$UserListbox(ForwardEvent evt) {
		Event origin = Events.getRealOrigin(evt);
		Image btn = (Image) origin.getTarget();
		Listitem litem = (Listitem) btn.getParent().getParent().getParent();
		Profile curUser = (Profile) litem.getValue();
		setSelectedUser(curUser);
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("selectedRecord", curUser);
		map.put("recordMode", "EDIT");
		map.put("parentWindow",userList);
		Executions.createComponents("UserCRUD.zul", null, map);
	}

	public void onView$UserListbox(ForwardEvent evt) {
		Event origin = Events.getRealOrigin(evt);
		Image btn = (Image) origin.getTarget();
		Listitem litem = (Listitem) btn.getParent().getParent().getParent();
		Profile curUser = (Profile) litem.getValue();
		setSelectedUser(curUser);
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("selectedRecord", curUser);
		map.put("recordMode", "READ");
		map.put("parentWindow",userList);
		Executions.createComponents("UserCRUD.zul", null, map);
	}

	
	/**
	 * This method is actualy an event handler triggered only from the edit
	 * dialog and it is responsible to reflect the data changes made to the
	 * list.
	 */
	@SuppressWarnings({ "unchecked" })
	public void onSaved(Event event) {
		Map<String, Object> args = (Map<String, Object>) event.getData();
		String recordMode = (String) args.get("recordMode");
		Profile curUser = (Profile) args.get("selectedRecord");
		if (recordMode.equals("NEW")) {
			appUsersList.add(curUser);
		}
		
		if (recordMode.equals("EDIT")) {
			appUsersList.set(appUsersList.indexOf(selectedUser),curUser);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void onDelete$UserListbox(ForwardEvent evt) {
		Event origin = Events.getRealOrigin(evt);
		Image btn = (Image) origin.getTarget();
		Listitem litem = (Listitem) btn.getParent().getParent().getParent();
		Profile curUser = (Profile) litem.getValue();
		setAppUsers(curUser);
		Messagebox.show("Are you sure to delete the user?", "Question",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event e) {
						if (Messagebox.ON_OK.equals(e.getName())) {

							CRUDService.delete(appUsers);
							// update the model of listbox
							appUsersList.remove(appUsers);
							setAppUsers(null);

						} else if (Messagebox.ON_CANCEL.equals(e.getName())) {
							// Cancel is clicked
						}
					}
				});
	}

	public BindingListModelList<Profile> getAppUsersList() {
		return appUsersList;
	}

	public void setAppUsersList(BindingListModelList<Profile> appUsersList) {
		this.appUsersList = appUsersList;
	}

	public Profile getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(Profile selectedUser) {
		this.selectedUser = selectedUser;
	}

	public Profile getAppUsers() {
		return appUsers;
	}

	public void setAppUsers(Profile appUsers) {
		this.appUsers = appUsers;
	}

}

package com.qainfotech.tap.training.resourceio;

import com.qainfotech.tap.training.resourceio.exceptions.ObjectNotFoundException;
import com.qainfotech.tap.training.resourceio.model.Individual;
import com.qainfotech.tap.training.resourceio.model.Team;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Ramandeep RamandeepSingh AT QAInfoTech.com
 */
public class TeamsJsonReader{
	List<Individual> listIndividuals;
	List<Individual> listInActiveIndividuals;
	List<Individual> listActiveIndividuals;
	JSONObject jsonObj;

	/**
	 * get a list of individual objects from db json file
	 * 
	 * @return 
	 * @throws ParseException 
	 * @throws IOException 
	 */
	public List<Individual> getListOfIndividuals() {
		// throw new UnsupportedOperationException("Not implemented.");
		JSONParser parser;
		JSONObject jsonObject = null;
		try {
			parser = new JSONParser();
			Object obj = parser.parse(new FileReader(new File(
					"D:\\assignment-resource-io\\src\\test\\resources\\db.json")));
			jsonObject = (JSONObject) obj;

		} catch (Exception e) {
			e.printStackTrace();
		}

		listIndividuals = new ArrayList<>();
		JSONArray individual = (JSONArray) jsonObject.get("individuals");
		for (int i = 0; i < individual.size(); i++) {
			JSONObject ob = (JSONObject) individual.get(i);
			Integer id = ((Long) ob.get("id")).intValue();
			// Integer id = Integer.parseInt((String) ob.get("id"));
			String name = ob.get("name").toString();
			Boolean active = (Boolean) ob.get("active");
			Map<String, Object> map = new HashMap();
			map.put("id", id);
			map.put("name", name);
			map.put("active", active);

			Individual ind1 = new Individual(map);
			listIndividuals.add(ind1);

		}
		return listIndividuals;
		
	}

	
	
	/**
	 * get individual object by id
	 * 
	 * @param id individual id
	 * @return 
	 * @throws com.qainfotech.tap.training.resourceio.exceptions.ObjectNotFoundException 
	 */
	public Individual getIndividualById(Integer id) throws ObjectNotFoundException{
		listIndividuals=getListOfIndividuals();

		Iterator<Individual> itr=listIndividuals.iterator();

		while(itr.hasNext()){
			Individual ind =itr.next();
			if(ind.getId()==id.intValue()){
				return ind;
			}
		}

		throw new ObjectNotFoundException("object not found", "Id", id.toString());


		//throw new UnsupportedOperationException("Not implemented.");
	}





	/**
	 * get individual object by name
	 * 
	 * @param name
	 * @return 
	 * @throws com.qainfotech.tap.training.resourceio.exceptions.ObjectNotFoundException 
	 */
	public Individual getIndividualByName(String name) throws ObjectNotFoundException{
		//throw new UnsupportedOperationException("Not implemented.");
		listIndividuals=getListOfIndividuals();

		Iterator<Individual> itr=listIndividuals.iterator();

		while(itr.hasNext()){
			Individual ind =itr.next();
			if(ind.getName().equalsIgnoreCase(name)){
				return ind;
			}
		}
		throw new ObjectNotFoundException("object not found", "Id", name);
	}





	/**
	 * get a list of individual objects who are not active
	 * 
	 * @return List of inactive individuals object
	 */
	public List<Individual> getListOfInactiveIndividuals(){
		// throw new UnsupportedOperationException("Not implemented.");
		listIndividuals=getListOfIndividuals();
		listInActiveIndividuals= new ArrayList<Individual>();
		Iterator<Individual> itr=listIndividuals.iterator();

		while(itr.hasNext()){
			Individual ind =itr.next();
			if(!(ind.isActive())){
				listInActiveIndividuals.add(ind);
			}
		}
		return listInActiveIndividuals;

	}




	/**
	 * get a list of individual objects who are active
	 * 
	 * @return List of active individuals object
	 */
	public List<Individual> getListOfActiveIndividuals(){
		//throw new UnsupportedOperationException("Not implemented.");
		listIndividuals=getListOfIndividuals();
		listActiveIndividuals= new ArrayList<Individual>();
		Iterator<Individual> itr=listIndividuals.iterator();

		while(itr.hasNext()){
			Individual ind =itr.next();
			if((ind.isActive())){
				listActiveIndividuals.add(ind);
			}
		}
		return listActiveIndividuals;
	}





	/**
	 * get a list of team objects from db json
	 * 
	 * @return 
	 */
	public List<Team> getListOfTeams(){
		JSONParser parser=new JSONParser();
		JSONObject jsonObj;
		List<Team> teamList=null;
		try {			
			
			parser = new JSONParser();
			Object obj = parser.parse(new FileReader(new File("D:\\assignment-resource-io\\src\\test\\resources\\db.json")));
			jsonObj = (JSONObject) obj;
			teamList=new ArrayList<>();
			Team team=null;
			JSONArray teamArray=(JSONArray)jsonObj.get("teams");
			for(int i=0;i<teamArray.size();i++){
				JSONObject object=(JSONObject) teamArray.get(i);
				Map<String, Object> map=(Map<String, Object>) object.clone();    	 
				team=new Team(map);
				teamList.add(team);
				//System.out.println(team);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return teamList;

	}
}

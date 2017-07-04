package com.qainfotech.tap.training.resourceio;

import com.qainfotech.tap.training.resourceio.exceptions.ObjectNotFoundException;
import com.qainfotech.tap.training.resourceio.model.Individual;
import com.qainfotech.tap.training.resourceio.model.Team;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;


/**
 *
 * @author Ramandeep RamandeepSingh AT QAInfoTech.com
 */
public class TeamsYamlReader{
    
    /**
     * get a list of individual objects from db yaml file
     * 
     * @return 
     */
    public List<Individual> getListOfIndividuals(){
        //throw new UnsupportedOperationException("Not implemented.");
    	ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("db.yaml").getFile());

		List<Individual> individualList = new ArrayList<>();

		Individual individualObject = null;
		try {
			InputStream inputStream = new FileInputStream(file);

			Yaml yaml = new Yaml();
			@SuppressWarnings("unchecked")
			Map<String, ArrayList> yamlParsers = (Map<String, ArrayList>) yaml.load(inputStream);

			ArrayList list = (ArrayList) yamlParsers.get("individuals");

			for (int i = 0; i < list.size(); i++) {
				@SuppressWarnings("unchecked")
				Map getValue = (Map<String, ArrayList>) list.get(i);
				individualObject = new Individual(getValue );
				individualList.add(individualObject);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return individualList;
    }
    
    /**
     * get individual object by id
     * 
     * @param id individual id
     * @return 
     * @throws com.qainfotech.tap.training.resourceio.exceptions.ObjectNotFoundException 
     */
    public Individual getIndividualById(Integer id) throws ObjectNotFoundException{
        //throw new UnsupportedOperationException("Not implemented.");
    	List<Individual> list= getListOfIndividuals();
    	Iterator i=list.iterator();
    	while(i.hasNext()) {
    		Individual ind=(Individual) i.next();
    		
    		if(ind.getId().compareTo(id) == 0) {
    			return ind;
    		}
    	}
    	throw new ObjectNotFoundException("Individual", "id", id.toString());
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
    	List<Individual> list= getListOfIndividuals();
    	Iterator i=list.iterator();
    	while(i.hasNext()) {
    		Individual ind=(Individual) i.next();
    		
    		if(ind.getName().equals(name)) {
    			return ind;
    		}
    	}
    	throw new ObjectNotFoundException("Individual", "Name", name);
    	
    }
    
    
    /**
     * get a list of individual objects who are not active
     * 
     * @return List of inactive individuals object
     */
    public List<Individual> getListOfInactiveIndividuals(){
        //throw new UnsupportedOperationException("Not implemented.");
    	List<Individual> list= getListOfIndividuals();
    	List<Individual> inactivelist=new ArrayList<Individual>();
    	Iterator i=list.iterator();
    	while(i.hasNext()) {
    		Individual ind=(Individual) i.next();
    		
    		if(!ind.isActive()) {
    			inactivelist.add(ind);
    		}
    	}
    	return inactivelist;
    }
    
    /**
     * get a list of individual objects who are active
     * 
     * @return List of active individuals object
     */
    public List<Individual> getListOfActiveIndividuals(){
        //throw new UnsupportedOperationException("Not implemented.");
    	List<Individual> list= getListOfIndividuals();
    	List<Individual> activelist=new ArrayList<Individual>();
    	Iterator i=list.iterator();
    	while(i.hasNext()) {
    		Individual ind=(Individual) i.next();
    		
    		if(ind.isActive()) {
    			activelist.add(ind);
    		}
    	}
    	return activelist;
    }
    
    /**
     * get a list of team objects from db yaml
     * 
     * @return 
     */
    public List<Team> getListOfTeams(){
        //throw new UnsupportedOperationException("Not implemented.");
    	ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("db.yaml").getFile());

		Map<String, Object> myMap = new HashMap<String, Object>();
		
		List<Team> teamList = new ArrayList<>();

		TeamsYamlReader reader = new TeamsYamlReader();
		Team teamObject = null;
		try {
			InputStream inputStream = new FileInputStream(file);
			Yaml yaml = new Yaml();
			@SuppressWarnings("unchecked")
			Map<String, ArrayList> yamlParsers = (Map<String, ArrayList>) yaml.load(inputStream);

			ArrayList list = (ArrayList) yamlParsers.get("teams");

			for (int i = 0; i < list.size(); i++) {
				List<Individual> individualList = new ArrayList<>();
				Map room = (Map<String, ArrayList>) list.get(i);

				myMap.put("name", room.get("name"));
				myMap.put("id", room.get("id"));

				ArrayList members = (ArrayList) room.get("members");
				for (int index = 0; index < members.size(); index++) {

					try {
						individualList.add(reader.getIndividualById((Integer) (members.get(index))));
					} catch (ObjectNotFoundException e) {

						e.printStackTrace();
					}
				}
				myMap.put("members", individualList);
				teamList.add(new Team(myMap));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return teamList;
    }
}

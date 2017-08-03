package heapdev.com.SocialMedia.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import heapdev.com.SocialMedia.Database.DatabaseClass;
import heapdev.com.SocialMedia.Exceptions.CustomizedException;
import heapdev.com.SocialMedia.Models.Profile;

public class ProfileServices {

	public static final Logger logger = LoggerFactory.getLogger(ProfileServices.class);

	private static Map<String, Profile> profileList = DatabaseClass.getProfiles();

	public ProfileServices() {

		Profile p = new Profile(1L, "abhinavece", "Abhinav", "Singh");
		Profile p1 = new Profile(2L, "ramesh.sachin", "Tom", "Moody");
		Profile p2 = new Profile(3L, "thakur.urvil", "Sarvesh", "Chandra");
		Profile p3 = new Profile(4L, "freekyStudd", "Chandra", "Mohan");
		Profile p4 = new Profile(5L, "getme", "Ali", "Asgar");

		profileList.put(p.getUsername(), p);
		profileList.put(p1.getUsername(), p1);
		profileList.put(p2.getUsername(), p2);
		profileList.put(p3.getUsername(), p3);
		profileList.put(p4.getUsername(), p4);

	}

	public List<Profile> getAllProfiles() throws Exception {

		if (profileList != null) {
			return new ArrayList<Profile>(profileList.values());
		} else {
			throw new CustomizedException("There is no profile existing in Database");
		}
	}

	public Profile getIndividualProfile(String username) throws Exception {

		if (profileList != null) {
			if (profileList.containsKey(username)) {
				logger.info("Data has been fetched successfully !!");
				return profileList.get(username);
			}
		} else {
			logger.error("There is no such username present in database");
		}
		throw new RuntimeException("There is an error while fetching profiles from database");
	}

	public Profile createNewProfile(Profile profile) {
		profile.setId(profileList.size() + 1);
		profileList.put(profile.getUsername(), profile);
		logger.info("New user " + profile.getFirstName() + " " + profile.getLastName()
				+ " has been added into the database");
		return profile;
	}

	public Profile updateProfile(String username, Profile profile) {
		try {
			if (profileList.containsKey(username)) {
				profileList.put(username, profile);
				logger.info("==> Profile with username " + username + " has been upgraded.");
				return profile;
			} else {
				logger.error("There is no profile with username: " + username);
				throw new CustomizedException("No Data Found !!");
			}
		} catch (Exception e) {
			logger.error("==>  No profiles present in database");
			throw new CustomizedException("No profiles present in database");
		}
	}

	public void deleteProfile(String username) {
		try {
			if (profileList.containsKey(username)) {
				profileList.remove(username);
				logger.info("user with username " + username + " has been deleted succesfully.");
			}
		} catch (Exception e) {
			throw new CustomizedException("No data found for deletion of a user!!");
		}
	}

}

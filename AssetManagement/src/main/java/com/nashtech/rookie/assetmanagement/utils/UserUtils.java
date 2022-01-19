package com.nashtech.rookie.assetmanagement.utils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nashtech.rookie.assetmanagement.repository.UserRepository;

@Service
public class UserUtils {

	@Autowired
	UserRepository userRepo;
	
	public UserUtils() {

	}

	private String prefix;
	private boolean isFirst;
	
	public String createUsername(String firstname, String lastName) {
		firstname = firstname.toLowerCase();
		lastName = lastName.toLowerCase();
		String splitString[] = firstname.split("\\s+");
		List<String> firstnamePart = Stream.of(firstname.split("\\s+")).map(elem -> new String(elem))
				.collect(Collectors.toList());
		prefix = firstnamePart.get(firstnamePart.size()-1);
		firstnamePart.remove(firstnamePart.size()-1);
		prefix +=  Stream.of(lastName.split("\\s+")).map(s -> String.valueOf(s.charAt(0))).reduce("", String::concat) + firstnamePart.stream().map(s -> String.valueOf(s.charAt(0))).reduce("", String::concat);
		List<String> prefixUsername = userRepo.getAllUsername(prefix);
		int max = userRepo.getAllUsername(prefix).stream()
													.map(o -> o.replace(prefix, ""))
													.map(x -> {
														if(x.equals("")) return 0;
														try {
															return Integer.parseInt(x);
											
														} catch (NumberFormatException  e) {
															//Logger
															return -1;
														}
																						
													}).mapToInt( v -> v).max().orElse(-1);
		
		return max==-1? prefix.toLowerCase():  prefix.toLowerCase() + String.valueOf(max+1) ;
	}
}
	
	

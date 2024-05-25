package com.Booking.Booking.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.Booking.Booking.entity.Rooms;
import com.Booking.Booking.service.impl.IFileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Booking.Booking.dto.PaginationResponseDTO;
import com.Booking.Booking.dto.UserDTO;
import com.Booking.Booking.entity.UserAccounts;
import com.Booking.Booking.entity.Users;
import com.Booking.Booking.repository.IUserAccountsRepository;
import com.Booking.Booking.repository.IUserRepository;
import com.Booking.Booking.service.impl.IUserServiceImpl;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService implements IUserServiceImpl {

	private final int pageSize = 10;

	@Autowired
	IUserRepository userRepository;

	@Autowired
	IUserAccountsRepository userAccountsRepository;

	@Autowired
	IFileServiceImpl fileServiceImpl;

	@Override
	public int addUser(MultipartFile file, String fullName, Date birthDay, boolean gender) {
		try {
			boolean isSaveFileSuccess = true;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String photoFilename = "nophoto.jpg";
			if (file != null && !file.isEmpty()) {
				isSaveFileSuccess = fileServiceImpl.saveFile(file);
				String formattedDate = dateFormat.format(new java.util.Date());
				photoFilename = formattedDate + "_" + file.getOriginalFilename();
			}
			Users users = new Users();
			if(isSaveFileSuccess) {
				users.setFullName(fullName);
				users.setBirthDay(birthDay);
				users.setGender(gender);
				users.setPhoto(photoFilename);
				userRepository.save(users);
			}
			return users.getId();
		} catch (Exception e) {
			System.out.print(e);
			return 0;
		}
	}

	@Override
	public boolean updateUser(MultipartFile file, int id, String fullName, Date birthDay, boolean gender) {
		boolean isUpdate = false;
		try {
			boolean saveFileSuccess = fileServiceImpl.saveFile(file);
			Optional<Users> getUser = userRepository.findById(id);
			String fileName = "";
			Users user = new Users();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			if(getUser.isPresent()){
				user = getUser.get();
				fileName = user.getPhoto();
				if (file != null && !file.isEmpty()) {
					saveFileSuccess = fileServiceImpl.saveFile(file);
					String formattedDate = dateFormat.format(new java.util.Date());
					fileName = formattedDate + "_" + file.getOriginalFilename();
				}
				if (saveFileSuccess) {
					user.setPhoto(fileName);
				}
				user.setFullName(fullName);
				user.setBirthDay(birthDay);
				user.setGender(gender);
				userRepository.save(user);
				isUpdate = true;
			}
			else{
				return isUpdate;
			}

		} catch (Exception e) {
			System.out.print(e.getMessage());
		}

		return isUpdate;
	}

	@Override
	public boolean deleteUser(int id) {

		boolean isDelete = false;
		try {
			Optional<Users> user = userRepository.findById(id);
			if (!user.isPresent())
				return isDelete;
			userRepository.deleteById(id);
			isDelete = true;
		} catch (Exception e) {
			System.out.print(e);
		}

		return isDelete;
	}

	@Override
	public PaginationResponseDTO<UserDTO> userList(int page, String searchValue) {
		if (searchValue != "")
			searchValue = "%" + searchValue + "%";
		Pageable pagination = PageRequest.of(page, pageSize);
		List<Users> users = userRepository.search(pagination, searchValue);
		List<UserDTO> listUsers = new ArrayList<UserDTO>();
		if (users.size() < 0)
			return null;

		for (Users item : users) {
			UserAccounts account = userAccountsRepository.findbyUserId(item.getId());

			String accountName;
			accountName = account==null? "":account.getUserName();

			listUsers.add(new UserDTO(item.getId(), item.getFullName(), item.getBirthDay(), item.isGender(),
					accountName,item.getPhoto()));
		}
		int rowCount = userRepository.getRowCount(searchValue);
		int pageCount = rowCount / pageSize;

		if (rowCount % pageSize > 0)
			++pageCount;

		return new PaginationResponseDTO<UserDTO>(listUsers, rowCount, pageCount);
	}

	@Override
	public List<UserDTO> getAllUser() {

		List<Users> users = userRepository.findAll();
		List<UserDTO> listUsers = new ArrayList<UserDTO>();

		if (users != null) {
			for (Users i : users) {
				UserAccounts account = userAccountsRepository.findbyUserId(i.getId());

				String accountName;
				accountName = account==null? "":account.getUserName();

				UserDTO user = new UserDTO();
				user.setId(i.getId());
				user.setFullName(i.getFullName());
				user.setBirthDay(i.getBirthDay());
				user.setGender(i.isGender());
				user.setPhoto(i.getPhoto());
				user.setAccountName(accountName);

				listUsers.add(user);
			}
		}
		return listUsers;
	}

	@Override
	public UserDTO getByUserId(int id) {

		Optional<Users> getUser = userRepository.findById(id);
		UserDTO user = new UserDTO();
		if (getUser.isPresent()) {
			UserAccounts account = userAccountsRepository.findbyUserId(getUser.get().getId());

			String accountName;
			accountName = account==null? "":account.getUserName();

			user.setId(getUser.get().getId());
			user.setFullName(getUser.get().getFullName());
			user.setFullName(getUser.get().getFullName());
			user.setBirthDay(getUser.get().getBirthDay());
			user.setGender(getUser.get().isGender());
			user.setPhoto(getUser.get().getPhoto());
			user.setAccountName(accountName);
		}
		return user;
	}

}

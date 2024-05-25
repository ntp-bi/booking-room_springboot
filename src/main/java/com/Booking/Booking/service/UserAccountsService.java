package com.Booking.Booking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Booking.Booking.contant.AppRole;
import com.Booking.Booking.dto.PaginationResponseDTO;
import com.Booking.Booking.dto.UserAccountDTO;
import com.Booking.Booking.entity.UserAccounts;
import com.Booking.Booking.entity.Users;
import com.Booking.Booking.repository.IRoleRepository;
import com.Booking.Booking.repository.IUserAccountsRepository;
import com.Booking.Booking.repository.IUserRepository;
import com.Booking.Booking.service.impl.IUserAccountsServiceImpl;
import com.Booking.Booking.util.JwtTokenProvider;

@Service
public class UserAccountsService implements IUserAccountsServiceImpl{

	private final int PAGE_SIZE = 5;

	@Autowired
	IUserAccountsRepository userAccountRepo;

	@Autowired
	IUserRepository userRepo;

	@Autowired
	IRoleRepository roleRepo;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@Autowired
	AuthenticationManager authenticationManager;

	@Override
	public String login(String username, String password) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, password));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtTokenProvider.generateToken(authentication);

		return token;
	}

	@Override
	public int add(UserAccountDTO payload) {
		// nếu username và password rỗng thì không thêm account
		if (payload.getUserName().equals("") || payload.getPassword().equals(""))
			return 0;

		// không thể thêm account với username đã tồn tại
		UserAccounts foundAccount = userAccountRepo.findByUserName(payload.getUserName());
		if (foundAccount != null)
			return 0;

		// không thể thêm account với user không tồn tại
		Optional<Users> foundUser = userRepo.findById(payload.getUserId());
		if (!foundUser.isPresent())
			return 0;

		// user đã có account thì không thể tạo thêm account khác
		UserAccounts foundAccountById = userAccountRepo.findbyUserId(payload.getUserId());
		if (foundAccountById != null)
			return 0;


//		UserAccounts data = userAccountsMapper.convertToEntity(payload, UserAccounts.class);
		UserAccounts data = new UserAccounts();
		data.setUserName(payload.getUserName());
		data.setPassword(passwordEncoder.encode(payload.getPassword()));
		data.setUser(foundUser.get());
//		// mọi account khi được thêm thì mặc định có role teacher
		data.setRole(roleRepo.findByRoleName(AppRole.TEACHER));

		userAccountRepo.save(data);
		return data.getId();
	}

	@Override
	public PaginationResponseDTO<UserAccountDTO> search(int page, String searchValue) {
		if (searchValue != "")
			searchValue = "%" + searchValue + "%";
		Pageable pagination = PageRequest.of(page, PAGE_SIZE);

		List<UserAccounts> accounts = userAccountRepo.search(pagination, searchValue);
		List<UserAccountDTO> result = new ArrayList<UserAccountDTO>();
		for (UserAccounts account : accounts)
			result.add(new UserAccountDTO(account.getId(), account.getUserName(), account.getPassword(),
					account.getUser().getFullName(), account.getUser().getId(), account.getRole().getId(),
					account.getRole().getRoleName()));
//			result.add(userAccountsMapper.convertToDto(account, UserAccountsDTO.class));

		int rowCount = userAccountRepo.rowCount(searchValue);
		int pageCount = rowCount / PAGE_SIZE;
		if (rowCount % PAGE_SIZE > 0)
			++pageCount;

		return new PaginationResponseDTO<UserAccountDTO>(result, rowCount, pageCount);
	}

	@Override
	public UserAccountDTO get(int userId) {
		Optional<UserAccounts> foundUserAccount = userAccountRepo.findById(userId);
		if (!foundUserAccount.isPresent())
			return null;

		UserAccountDTO result = new UserAccountDTO();
		result.setId(foundUserAccount.get().getId());
		result.setPassword(foundUserAccount.get().getPassword());
		result.setFullName(foundUserAccount.get().getUser().getFullName());
		result.setRoleId(foundUserAccount.get().getRole().getId());
		result.setRoleName(foundUserAccount.get().getRole().getRoleName());
		result.setUserId(foundUserAccount.get().getUser().getId());
		result.setUserName(foundUserAccount.get().getUserName());
//		UserAccountsDTO result = userAccountsMapper.convertToDto(foundUserAccount.get(), UserAccountsDTO.class);

		return result;
	}

	@Override
	public boolean update(int userId, UserAccountDTO payload) {
//		System.out.println("userId = " + userId);
//		System.out.println(payload.toString());
		Optional<UserAccounts> found = userAccountRepo.findById(userId);
//		System.out.println("found : " + found.get().getId());
		if (!found.isPresent())
			return false;
		UserAccounts foundAccount = found.get();

		if (payload.getPassword() != null)
			foundAccount.setPassword(passwordEncoder.encode(payload.getPassword()));
		if (payload.getRoleId() > 0)
			foundAccount.setRole(roleRepo.findById(payload.getRoleId()).get());
		if (payload.getUserId() > 0)
			foundAccount.setUser(userRepo.findById(payload.getUserId()).get());
		if (payload.getUserName() != null && !payload.getUserName().equals(foundAccount.getUserName()))
			foundAccount.setUserName(payload.getUserName());

		userAccountRepo.save(foundAccount);

		return true;
	}

	@Override
	public boolean delete(int userId) {
		Optional<UserAccounts> found = userAccountRepo.findById(userId);
		if (!found.isPresent())
			return false;

		userAccountRepo.delete(found.get());

		return true;
	}

	@Override
	public String changePassword(String userName, String oldPassword, String newPassword, String confirmPassword) {
		UserAccounts found = userAccountRepo.findByUserName(userName);
		// old password is wrong
		if (found.getPassword().equals(passwordEncoder.encode(oldPassword)))
			return "Old password is wrong";

		// new password is the same as old password
		if (oldPassword.equals(newPassword))
			return "New Password is the same as old password";

		// confirm password is not the same new password
		if (!newPassword.equals(confirmPassword))
			return "Confirm password is not the same as new password";

		found.setPassword(passwordEncoder.encode(newPassword));
		userAccountRepo.save(found);
		return "";
	}

	@Override
	public List<UserAccountDTO> list() {
		List<UserAccounts> accounts = userAccountRepo.findAll();
		List<UserAccountDTO> result = new ArrayList<>();

		for (UserAccounts account : accounts)
			result.add(new UserAccountDTO(account.getId(), account.getUserName(), account.getPassword(),
					account.getUser().getFullName(), account.getUser().getId(), account.getRole().getId(),
					account.getRole().getRoleName()));
//			result.add(userAccountsMapper.convertToDto(account, UserAccountsDTO.class));

		return result;
	}


}

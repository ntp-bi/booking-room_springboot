package com.Booking.Booking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Booking.Booking.dto.PaginationResponseDTO;
import com.Booking.Booking.dto.RoomTypeDTO;
import com.Booking.Booking.entity.RoomTypes;
import com.Booking.Booking.repository.IRoomTypeRepository;
import com.Booking.Booking.service.impl.IRoomTypeServiceImpl;


@Service
public class RoomTypeService implements IRoomTypeServiceImpl{
	
	private final int pageSize = 10;

	@Autowired
	IRoomTypeRepository roomTypeRepository;
	
	@Override
	public int addRoomType(String roomTypeName) {
		
		try {
			List<RoomTypes> roomTypes = roomTypeRepository.findAll();
			for (RoomTypes roomType : roomTypes){
				if(roomTypeName.equalsIgnoreCase(roomType.getTypeName())){
					return 0;
				}
			}

			RoomTypes roomType = new RoomTypes();
			roomType.setTypeName(roomTypeName);
			
			roomTypeRepository.save(roomType);			
			return roomType.getId();
		} catch (Exception e) {
			System.out.print(e);
			return 0;
		}
	}

	@Override
	public boolean updateRoomType(int id, String roomTypeName) {
		
		boolean isUpdate = false;
		try {
			List<RoomTypes> roomTypes = roomTypeRepository.findAllRoomType(id);
			for (RoomTypes r : roomTypes){
				if(roomTypeName.equalsIgnoreCase(r.getTypeName())){
					return isUpdate;
				}
			}
			Optional<RoomTypes> getRoomType = roomTypeRepository.findById(id);
			if(!getRoomType.isPresent()) return isUpdate;
			
			RoomTypes roomType = getRoomType.get();
			roomType.setTypeName(roomTypeName);			
            roomTypeRepository.save(roomType);			
			isUpdate = true;
		} catch (Exception e) {
			System.out.print(e);
		}

		return isUpdate;
	}

	@Override
	public boolean deleteRoomType(int id) {

		boolean isDelete = false;
		try {
			Optional<RoomTypes> roomType = roomTypeRepository.findById(id);
			if(!roomType.isPresent()) return isDelete;
			roomTypeRepository.deleteById(id);
            isDelete = true;			
		} catch (Exception e) {
			System.out.print(e);
		}

		return isDelete;
	}

	@Override
	public PaginationResponseDTO<RoomTypeDTO> roomTypeList(int page, String searchValue) {
		if((searchValue) != "") searchValue="%" + searchValue + "%"; 
		Pageable pagination = PageRequest.of(page, pageSize);
		List<RoomTypes> roomTypes = roomTypeRepository.search(pagination, searchValue);
		
		List<RoomTypeDTO> listRoomTypes= new ArrayList<RoomTypeDTO>();
;
		if(roomTypes.size() < 0) return null;
		
		for(RoomTypes item : roomTypes)
			listRoomTypes.add(new RoomTypeDTO(item.getId(), item.getTypeName()));
		int rowCount = roomTypeRepository.getRowCount(searchValue);
		int pageCount = rowCount/pageSize;
		
		if (rowCount%pageSize>0) ++pageCount;
		
		
		return new PaginationResponseDTO<RoomTypeDTO>(listRoomTypes, rowCount, pageCount);
	}

	@Override
	public List<RoomTypeDTO> getAllRoomType() {
		
		List<RoomTypes> roomTypes = roomTypeRepository.findAll();
		List<RoomTypeDTO> listRoomTypes = new ArrayList<RoomTypeDTO>();
		
		if(roomTypes != null) {
			for (RoomTypes i : roomTypes) {
				RoomTypeDTO roomType = new RoomTypeDTO();
				roomType.setId(i.getId());
				roomType.setTypeName(i.getTypeName());
				listRoomTypes.add(roomType);
			}
		}
		
		return listRoomTypes;
	}

	@Override
	public RoomTypeDTO getByRoomTypeId(int id) {
		Optional<RoomTypes> getRoomTypes= roomTypeRepository.findById(id);
		RoomTypeDTO result = new RoomTypeDTO();	
		if(getRoomTypes != null)
		{
			result.setId(getRoomTypes.get().getId());
			result.setTypeName(getRoomTypes.get().getTypeName());
		}
		return result;
	}

}

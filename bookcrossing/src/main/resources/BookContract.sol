pragma solidity ^0.4.10;

contract Book4 {
    address admin;
    uint constant duration = 90*24*60*60; // in seconds
    struct StudentInfo{
        string schoolName;
        string emailAddr;
        string[] donatedBooksId;
        uint donatedCount;
        mapping(string=>bool) borrowedBooksId;
        uint borrowedCount;
        mapping(string=>bool) wantBooksId;
    }
    struct BookInfo {
        string studentId;
        string bookName;
        string bookCategory;
    }
    struct BookStatus{
        string studentId;
        bool avail;
        uint want;
        uint maxBorrowDuration; // in seconds
        uint startBorrowTime;
    }
    
    mapping (string => StudentInfo) studentsMap;
    uint numStudents;
    mapping (string => BookInfo) booksInfoMap;
    uint numBooks;
    mapping (string => BookStatus) bookStatusMap;
    mapping (string => string[]) bookCategoryMap;
    mapping (string => uint) bookCategoryCountMap;
    
    function Book4() public {
        admin = msg.sender;
    }
    
	modifier stringNotEmpty(string s) {
		require (bytes(s).length > 0);
		_;
	}
	
    modifier borrowedLessThanDonated(string _studId) {
        require(studentsMap[_studId].borrowedCount < studentsMap[_studId].donatedCount);
        _;
    }
    
	modifier bookIsExisted(string _bookId) {
		require (bytes(booksInfoMap[_bookId].studentId).length > 0);
		_;
	}
	
	modifier categoryIsExisted(string _category) {
		require(bookCategoryCountMap[_category] > 0);
		_;
	}
	
    function registerStudent(string _studId, string _bookId, string _emailAddr, string _bookName, string _schoolName, string _bookCategory) public 
		stringNotEmpty(_studId) stringNotEmpty(_bookId) stringNotEmpty(_emailAddr) stringNotEmpty(_bookName) stringNotEmpty(_schoolName) 
		stringNotEmpty(_bookCategory){
        studentsMap[_studId].schoolName = _schoolName;
        studentsMap[_studId].emailAddr = _emailAddr;
        studentsMap[_studId].donatedCount = studentsMap[_studId].donatedCount + 1;
        studentsMap[_studId].donatedBooksId.push(_bookId);
        
        BookInfo memory tmpBookInfo = BookInfo(_studId, _bookName, _bookCategory);
        booksInfoMap[_bookId] = tmpBookInfo;

        BookStatus memory tmpBookStatus = BookStatus(_studId, true, 0, duration, 0);
        bookStatusMap[_bookId] = tmpBookStatus;
        
        bookCategoryMap[_bookCategory].push(_bookId);
        bookCategoryCountMap[_bookCategory]++;
    }
    
    function getStudent(string _studId) public constant stringNotEmpty(_studId) returns (string, uint, string, uint){
        StudentInfo memory tmpStudentInfo = studentsMap[_studId];
        return (tmpStudentInfo.schoolName, tmpStudentInfo.donatedCount, tmpStudentInfo.donatedBooksId[tmpStudentInfo.donatedCount-1], tmpStudentInfo.borrowedCount);
    }
    
    function checkBookStatus(string _bookId) public constant stringNotEmpty(_bookId) bookIsExisted(_bookId) returns (string, bool, uint, uint, uint){
        BookStatus memory tmpBookStatus = bookStatusMap[_bookId];
        return (tmpBookStatus.studentId, tmpBookStatus.avail, tmpBookStatus.want, tmpBookStatus.maxBorrowDuration, tmpBookStatus.startBorrowTime);
    }
    
    function getBookInfo(string _bookId) public constant stringNotEmpty(_bookId) bookIsExisted(_bookId) returns (string, string, string) {
        BookInfo memory tmpBookInfo =  booksInfoMap[_bookId];
        return (tmpBookInfo.studentId, tmpBookInfo.bookName, tmpBookInfo.bookCategory);
    }

    function borrowBook(string _bookId, string _studId) public borrowedLessThanDonated(_studId) bookIsExisted(_bookId)
		stringNotEmpty(_bookId) stringNotEmpty(_studId){
        returnBook(_bookId);

        studentsMap[_studId].borrowedBooksId[_bookId] = true;
        studentsMap[_studId].borrowedCount ++ ;
        
        bookStatusMap[_bookId].avail = false;
        bookStatusMap[_bookId].studentId = _studId;
        bookStatusMap[_bookId].startBorrowTime = now;
        if (studentsMap[_studId].wantBooksId[_bookId]){
            bookStatusMap[_bookId].want--;
            delete studentsMap[_studId].wantBooksId[_bookId];
        }
    }
    
    function wantBook(string _bookId, string _studId) bookIsExisted(_bookId) 
		stringNotEmpty(_bookId) stringNotEmpty(_studId) {
        bookStatusMap[_bookId].want++;
        studentsMap[_studId].wantBooksId[_bookId] = true;
    }
    
    // Return the book
    function returnBook(string _bookId) public bookIsExisted(_bookId) stringNotEmpty(_bookId) {
        string memory studentId;
        bool avail;
        uint want;
        uint startBorrowTime;
        uint maxBorrowDuration;
        (studentId, avail, want, maxBorrowDuration, startBorrowTime) = checkBookStatus(_bookId);
        if (!avail) {
            bookStatusMap[_bookId].avail = true;
            delete studentsMap[studentId].borrowedBooksId[_bookId];
            studentsMap[studentId].borrowedCount--;
        }
    }
    
    function checkOverdue(string _bookId) public constant bookIsExisted(_bookId) stringNotEmpty(_bookId) returns (bool){
        if ((!bookStatusMap[_bookId].avail) && (now - bookStatusMap[_bookId].startBorrowTime > bookStatusMap[_bookId].maxBorrowDuration)) {
            returnBook(_bookId);
            return true;
        } else {
            return false;
        }
    }
    
    function updateMaxBorrowDuration(string _bookId, uint _newDuration) bookIsExisted(_bookId) stringNotEmpty(_bookId){
        bookStatusMap[_bookId].maxBorrowDuration = _newDuration;
    }
    
    function concatStrWithSpace(string _a, string _b) internal constant returns (string) {
      bytes memory _ba = bytes(_a);
      bytes memory _bb = bytes(_b);
      string memory ab = new string(_ba.length + _bb.length + 1);
      bytes memory bab = bytes(ab);
      uint k = 0;
      for (uint i = 0; i < _ba.length; i++) bab[k++] = _ba[i];
      // Add a space to separate different strings
      bab[k++] = ' ';
      for (i = 0; i < _bb.length; i++) bab[k++] = _bb[i];
      return string(bab);
  }
    
    function getBooksOfCategory(string _bookCategory) public constant categoryIsExisted(_bookCategory) returns (string){
        uint cnt = bookCategoryCountMap[_bookCategory];
        uint i = 0;
        string memory concatResult;
        for (i ; i< cnt; i++) {
            concatResult = concatStrWithSpace(concatResult, bookCategoryMap[_bookCategory][i]);
        }
        return concatResult;
    }
    
}

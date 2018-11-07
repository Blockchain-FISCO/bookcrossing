pragma solidity ^0.4.2;

contract BookDrift {
    address admin;
    struct StudentInfo{
        address schoolAddr;
        string bookId;
        string emailAddr;
    }
    struct BookInfo {
        string studentId;
        string bookName;
    }
    struct BookStatus{
        string studentId;
        bool avail;
        address schoolAddr;
    }
    mapping (address => string) schools;
    uint numSchools;
    mapping (string => address) addressOfSchools;
    mapping (string => StudentInfo) students;
    uint numStudents;
    mapping (string => BookInfo) books;
    uint numBooks;
    mapping (string => BookStatus) bookLocations;
    
    //合约的构造函数
    function BookDrift public {
        admin = msg.sender;
    }
    
    //学院信息管理类
    function registerSchool(string _schoolName) public {
        schools[msg.sender] = _schoolName;
        addressOfSchools[_schoolName] = msg.sender;
        numSchools++;
    }
    
    function getAddressOfSchool(string _schoolName) public constant returns (address) {
        return addressOfSchools[_schoolName];
    }
    
    function getSchoolOfAddress(address _schoolAddress) public constant returns (string) {
        return schools[_schoolAddress];
    }
    
    //学生信息管理类
    function registerStudent(string _studId, string _bookId, string _emailAddr, string _bookName) public {
        StudentInfo memory tmpStudentInfo = StudentInfo(msg.sender, _bookId, _emailAddr);
        students[_studId] = tmpStudentInfo;

        BookInfo memory tmpBookInfo = BookInfo(_studId, _bookName);
        books[_bookId] = tmpBookInfo;
        
        BookStatus memory tmpBookStatus = BookStatus("", true, msg.sender);
        bookLocations[_bookId] = tmpBookStatus; 
    }
    
    function getStudent(string _studId) public constant returns (address, string, string) {
        StudentInfo memory tmpStudentInfo = students[_studId];
        return (tmpStudentInfo.schoolAddr, tmpStudentInfo.bookId, tmpStudentInfo.emailAddr);
    }
    
    //书籍信息管理类
    function checkBookStatus(string _bookId) public constant returns (string, bool, address){
        BookStatus memory tmpBookStatus = bookLocations[_bookId];
        return (tmpBookStatus.studentId, tmpBookStatus.avail, tmpBookStatus.schoolAddr);
    }
    
    function getBookInfo(string _bookId) public constant returns (string, string) {
        BookInfo memory tmpBookInfo =  books[_bookId];
        return (tmpBookInfo.studentId, tmpBookInfo.bookName);
    }

    function borrowBook(string _bookId, string _studId) public {
        BookStatus memory tmpBookStatus = BookStatus(_studId, false, address(0));
        bookLocations[_bookId] = tmpBookStatus;
    }   
    
    // Reset the book status as available by the student
    function resetBookStatus(string _bookId) public {
        string memory studentId;
        bool avail;
        address schoolAddr;
        (studentId, avail, schoolAddr) = checkBookStatus(_bookId);
        BookStatus memory tmpBookStatus = BookStatus(studentId, true, schoolAddr);
        bookLocations[_bookId] = tmpBookStatus;
    }
    
    
}
